package com.trakknamur.demo.mappers;

import com.trakknamur.demo.models.dtos.ParcoursDTO;
import com.trakknamur.demo.models.entities.Parcours;
import com.trakknamur.demo.models.forms.ParcoursForm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Deprecated
@Service
public class ParcoursMapper implements BaseMapper<Parcours, ParcoursForm, ParcoursDTO> {

    private final TrouMapper trouMapper;

    public ParcoursMapper(TrouMapper trouMapper) {
        this.trouMapper = trouMapper;
    }

    public Parcours fromFormToEntity(ParcoursForm parcoursForm) {
        Parcours p = new Parcours();
        p.setNom(parcoursForm.getNom());
        p.setTypeParcours(parcoursForm.getTypeParcours());
        p.setEmail(parcoursForm.getEmail());
        if (parcoursForm.getTrous() != null) {
            p.setTrous(parcoursForm.getTrous().stream()
                    .map(trouMapper::fromFormToEntity)
                    .collect(Collectors.toList()));
        }

        return p;
    }

    public ParcoursDTO toDto(Parcours parcours) {
        if (parcours != null && parcours.getIdParcours() > 0) {
            return ParcoursDTO
                    .builder()
                    .id(parcours.getIdParcours())
                    .nom(parcours.getNom())
                    .email(parcours.getEmail())
                    .dateCreation(parcours.getDateCreation())
                    .dateModification(parcours.getDateModification())
                    .typeParcours(parcours.getTypeParcours())
                    .nbJoursDepuisCreation(generateNbJoursDepuisCreation(parcours.getDateCreation()))
                    .trous(parcours.getTrous() != null ? parcours.getTrous().stream()
                            .map(trouMapper::toDto)
                            .collect(Collectors.toList()) : null)
                    .build();
        }
        return null;
    }

    public Parcours toEntity(ParcoursDTO dto) {
        Parcours p = new Parcours();
        if (dto != null && dto.getId() > 0) {
            p.setIdParcours(dto.getId());
            p.setNom(dto.getNom());
            p.setTypeParcours(dto.getTypeParcours());
            p.setDateCreation(dto.getDateCreation());
            p.setDateModification(dto.getDateModification());
            p.setEmail(dto.getEmail());
            p.setTrous(dto.getTrous() != null ? dto.getTrous().stream()
                    .map(trouMapper::toEntity)
                    .collect(Collectors.toList()) : null);
        }
        return p;
    }

    private int generateNbJoursDepuisCreation(Instant dateCreation) {
        long temp = Instant.now().getEpochSecond() - dateCreation.getEpochSecond();
        return (int) (temp / (60 * 60 * 24));
    }

}
