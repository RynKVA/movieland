package com.rynkovoi.repository;

import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.model.Movie;
import com.rynkovoi.type.SortDirection;
import com.rynkovoi.type.SortType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.rynkovoi.model.Movie_.description;
import static com.rynkovoi.model.Movie_.nameNative;
import static com.rynkovoi.model.Movie_.nameRussian;
import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class CriteriaMovieRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public Page<Movie> findAll(MovieFilter movieFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = query.from(Movie.class);

        query.where(criteriaBuilder.and(buildPredicates(criteriaBuilder, root, movieFilter)));
        sort(criteriaBuilder, root, query, movieFilter);

        return paginating(criteriaBuilder, query, movieFilter);
    }

    private Predicate[] buildPredicates(CriteriaBuilder criteriaBuilder, Root<Movie> root, MovieFilter movieFilter) {
        List<Predicate> predicates = new ArrayList<>();
        String searchText = movieFilter.getSearchText();
        if ((nonNull(searchText) && !searchText.isBlank())) {
            String likePattern = String.join(searchText.toLowerCase(), "%", "%");
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(nameNative)), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(nameRussian)), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(description)), likePattern)
            ));
        }
        return predicates.toArray(new Predicate[0]);
    }

    private void sort(CriteriaBuilder criteriaBuilder, Root<Movie> root, CriteriaQuery<Movie> query, MovieFilter movieFilter) {
        SortType sortType = movieFilter.getSortType();
        if (nonNull(sortType)) {
            Path<Object> path = root.get(sortType.getName());
            query.orderBy(movieFilter.getSortDirection().equals(SortDirection.DESC) ? criteriaBuilder.desc(path) : criteriaBuilder.asc(path));
        }
    }

    private Page<Movie> paginating(CriteriaBuilder criteriaBuilder, CriteriaQuery<Movie> query, MovieFilter movieFilter) {
        int page = movieFilter.getPage();
        int size = movieFilter.getSize();
        TypedQuery<Movie> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);
        List<Movie> results = typedQuery.getResultList();
        return new PageImpl<>(results, PageRequest.of(movieFilter.getPage(), movieFilter.getSize()), totalCount(criteriaBuilder));
    }

    private long totalCount(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Movie> countRoot = countQuery.from(Movie.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
