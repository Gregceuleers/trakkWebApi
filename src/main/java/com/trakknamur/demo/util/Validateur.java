package com.trakknamur.demo.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validateur {

    public static void validerReglesMetier(RegleMetier... regles) throws Exception {

        for (RegleMetier regle : regles) {

            if (regle == null || regle.getRegle() == null || regle.getSinon() == null) {
                log.error("Règle métier mal configurée : " + regle);
                throw new NullPointerException();
            }

            try {
                if (regle.getSi() == null || regle.getSi()) {
                    if (regle.isDoitRetourner()) {
                        if (!regle.getRegle().getAsBoolean()) {
                            throw regle.getSinon().get();
                        }
                    } else {
                        if (regle.getRegle().getAsBoolean()) {
                            throw regle.getSinon().get();
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Exception lors de l'évaluation d'une règle métier : " + e.getClass().getSimpleName() + " (" + e.getMessage() + ")");
                log.debug("Stacktrace", e);
                throw e;
            }
        }

    }
}
