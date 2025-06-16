/*
package com.rynkovoi.service.parser;

import com.rynkovoi.service.impl.DefaultGenresService;
import com.rynkovoi.web.dto.ParsedMovie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileParser {

    private final DefaultGenresService genresCacheService;
    private static final Pattern URL_PATTERN = Pattern.compile(
            "(https?://\\S+)", Pattern.CASE_INSENSITIVE
    );


    public List<ParsedMovie> parseMovieFromFile(MultipartFile movies, MultipartFile posters) throws IOException {
        return parseToParsedMovie(getContentFromFile(movies), parseUrlFromFile(posters));
    }

    public List<String> parseGenreFromFile(MultipartFile genres) throws IOException {
        return parseToGenreName(getContentFromFile(genres));
    }

    private List<String> parseUrlFromFile(MultipartFile file) throws IOException {
        String content = getContentFromFile(file);
        return escapeUnnecessaryRowsAndSymbols(content).stream()
                .map(FileParser::extractUrl)
                .toList();
    }

    private String getContentFromFile(MultipartFile file) throws IOException {
        log.info("Parsing file: {}", file.getOriginalFilename());
        byte[] bytes = file.getBytes();
        return new String(bytes);
    }

    */
/**
     Utils
     *//*


    private List<ParsedMovie> parseToParsedMovie(String content, List<String> posters) {
        List<List<String>> blocks = parseFileOnBlocks(content);
        List<ParsedMovie> movies = new ArrayList<>();
        int posterIndex = 0;
        for (List<String> block : blocks) {
            Iterator<String> iterator = block.iterator();
            String[] splitNames = iterator.next().split("/");
            movies.add(ParsedMovie.builder()
                    .nameRussian(splitNames[0])
                    .nameNative(splitNames[1])
                    .yearOfRelease(Integer.parseInt(iterator.next()))
                    .releaseCountry(iterator.next())
                    .genres(getGenresIdFromNames(iterator.next()))
                    .description(iterator.next())
                    .rating(Double.parseDouble((iterator.next()).replace("rating:", "").trim()))
                    .price(Double.parseDouble((iterator.next()).replace("price:", "").trim()))
                    .picturePath(posters.get(posterIndex++))
                    .build());
        }
        return movies;
    }

    private List<String> parseToGenreName(String content) {
        return escapeUnnecessaryRowsAndSymbols(content);
    }

    private Integer[] getGenresIdFromNames(String names) {
        return Arrays.stream(names.split(", "))
                .map(genresCacheService::getGenreIdByName)
                .toArray(Integer[]::new);
    }

    private List<List<String>> parseFileOnBlocks(String content) {
        List<List<String>> blocks = new ArrayList<>();
        List<String> currentBlock = new ArrayList<>();

        List<String> lines = escapeUnnecessaryRowsAndSymbols(content);
        for (String line : lines) {
            line = line.trim();
            currentBlock.add(line);
            if (line.startsWith("price:")) {
                blocks.add(new ArrayList<>(currentBlock));
                currentBlock.clear();
            }
        }
        return blocks;
    }

    private List<String> escapeUnnecessaryRowsAndSymbols(String content) {
        String[] lines = content.replace("\r", "").replace("\uFEFF", "").split("\n");
        return Arrays.stream(lines).filter(s -> !s.isEmpty() && !s.equals("\r"))
                .toList();
    }

    public static String extractUrl(String content) {
        Matcher matcher = URL_PATTERN.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

}*/
