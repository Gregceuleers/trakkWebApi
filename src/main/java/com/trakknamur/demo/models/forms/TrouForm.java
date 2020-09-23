package com.trakknamur.demo.models.forms;

import com.trakknamur.demo.models.entities.Parcours;
import com.trakknamur.demo.models.enums.TypeTrou;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrouForm {

    TypeTrou typeTrou;

    int distance;

    Long idParcours;
}
