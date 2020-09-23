package com.trakknamur.demo.util;

import com.trakknamur.demo.models.dtos.ParcoursDTO;
import com.trakknamur.demo.models.forms.TrouForm;
import com.trakknamur.demo.services.ParcoursService;
import org.springframework.stereotype.Component;

@Component
public class GolfUtils {

    private final ParcoursService parcoursService;

    public GolfUtils(ParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    public boolean checkIfNbHolesNotExceededTypeParcours(TrouForm trou) {

        ParcoursDTO parcoursDTO = parcoursService.getOne(trou.getIdParcours());

        int nbHoles = parcoursDTO.getTrous().size();

        return nbHoles < parcoursDTO.getTypeParcours().getNbHoles();
    }

}
