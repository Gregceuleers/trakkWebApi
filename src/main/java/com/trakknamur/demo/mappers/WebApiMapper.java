package com.trakknamur.demo.mappers;

import com.trakknamur.demo.models.dtos.ParcoursDTO;
import com.trakknamur.demo.models.dtos.TrouDTO;
import com.trakknamur.demo.models.dtos.UserDTO;
import com.trakknamur.demo.models.entities.Parcours;
import com.trakknamur.demo.models.entities.Trou;
import com.trakknamur.demo.models.entities.User;
import com.trakknamur.demo.models.forms.ParcoursForm;
import com.trakknamur.demo.models.forms.TrouForm;
import com.trakknamur.demo.models.forms.UserForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper()
public interface WebApiMapper {

    @Mappings({
            @Mapping(source = "idParcours", target = "id"),
            @Mapping(target = "nbJoursDepuisCreation", source = "entity", qualifiedByName = "generateNbJours")
    })
    ParcoursDTO toDto(Parcours entity);

    @Mappings({
            @Mapping(source = "idTrou", target = "id")
    })
    TrouDTO toDto(Trou entity);

    @Named("generateNbJours")
    default int generateNbDaysFromCreation(Parcours entity) {
        long temp = Instant.now().getEpochSecond() - entity.getDateCreation().getEpochSecond();
        return (int) (temp / (60 * 60 * 24) + 1);
    }

    @Mappings({
            @Mapping(source = "id", target = "idParcours")
    })
    Parcours toEntity(ParcoursDTO dto);

    @Mappings({
            @Mapping(target = "idTrou", source = "id")
    })
    Trou toEntity(TrouDTO dto);

    @Mappings({
            @Mapping(target = "idTrou", ignore = true),
            @Mapping(target = "parcours.idParcours", source = "idParcours"),
    })
    Trou fromFormToEntity(TrouForm form);

    @Mappings({
            @Mapping(target = "idParcours", ignore = true),
            @Mapping(target = "dateCreation", ignore = true),
            @Mapping(target = "dateModification", ignore = true),
            @Mapping(target = "trous", ignore = true)

    })
    Parcours fromFormToEntity(ParcoursForm form);

    @Mappings({})
    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "authorities", ignore = true)
    })
    User toEntity(UserDTO dto);

    @Mappings({
            @Mapping(target = "idUser", ignore = true),
            @Mapping(target = "accountNonExpired", ignore = true),
            @Mapping(target = "accountNonLocked", ignore = true),
            @Mapping(target = "credentialsNonExpired", ignore = true),
            @Mapping(target = "enabled", ignore = true),
            @Mapping(target = "authorities", ignore = true)
    })
    User fromFormToEntity(UserForm form);




}
