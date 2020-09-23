package com.trakknamur.demo.mappers;

import com.trakknamur.demo.models.dtos.TrouDTO;
import com.trakknamur.demo.models.entities.Trou;
import com.trakknamur.demo.models.forms.TrouForm;
import com.trakknamur.demo.services.ParcoursService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Deprecated
@Service
public class TrouMapper implements BaseMapper<Trou, TrouForm, TrouDTO>{

    private final ParcoursService parcoursService;

    private final ParcoursMapper parcoursMapper;

    public TrouMapper(@Lazy ParcoursService parcoursService, @Lazy ParcoursMapper parcoursMapper) {
        this.parcoursService = parcoursService;
        this.parcoursMapper = parcoursMapper;
    }

    public Trou fromFormToEntity(TrouForm trouForm) {
        Trou t = new Trou();
        t.setTypeTrou(trouForm.getTypeTrou());
        t.setDistance(trouForm.getDistance());
        t.setParcours(
                this.parcoursMapper.toEntity(
                        this.parcoursService.getOne(trouForm.getIdParcours())
                ));
        return t;
    }

    public TrouDTO toDto(Trou trou) {
        TrouDTO dto = new TrouDTO();
        if (trou != null) {
            dto.setId(trou.getIdTrou());
            dto.setDistance(trou.getDistance());
            dto.setTypeTrou(trou.getTypeTrou());
            dto.setParcours(trou.getParcours());
        }
        return dto;
    }

    public Trou toEntity(TrouDTO dto) {
        Trou trou = new Trou();
        if (dto != null) {
            trou.setIdTrou(dto.getId());
            trou.setDistance(dto.getDistance());
            trou.setTypeTrou(dto.getTypeTrou());
            trou.setParcours(dto.getParcours());
        }
        return trou;
    }
}
