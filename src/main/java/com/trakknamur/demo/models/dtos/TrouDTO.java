package com.trakknamur.demo.models.dtos;


import com.trakknamur.demo.models.entities.Parcours;
import com.trakknamur.demo.models.enums.TypeTrou;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrouDTO {

    long id;

    TypeTrou typeTrou;

    int distance;

    Parcours parcours;
}
