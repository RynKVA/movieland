package com.rynkovoi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "release_countries")
@Entity
public class ReleaseCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "release_countries_id_sequence")
    @SequenceGenerator(name = "release_countries_id_sequence", sequenceName = "release_countries_id_sequence")
    private int id;
    private String name;
}
