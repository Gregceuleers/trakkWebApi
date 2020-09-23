package com.trakknamur.demo.models.dtos;

import com.trakknamur.demo.models.enums.TypeParcours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParcoursDTO {

    long id;

    String nom;

    String email;

    TypeParcours typeParcours;

    Instant dateCreation;

    Instant dateModification;

    int nbJoursDepuisCreation;

    List<TrouDTO> trous;
}
