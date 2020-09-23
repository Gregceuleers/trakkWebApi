package com.trakknamur.demo.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.trakknamur.demo.models.enums.TypeTrou;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity(name = "trou")
@Table(name = "trou")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trou {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long idTrou;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_trou", nullable = false)
    TypeTrou typeTrou;

    @Column(name = "distance")
    int distance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    Parcours parcours;

}
