package com.trakknamur.demo.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegleMetier {

    /**
     * Précondition du test (optionnel)
     */
    Boolean si;

    /**
     * Test à effectuer
     */
    BooleanSupplier regle;

    /**
     * Résultat attendu du test
     */
    boolean doitRetourner;

    /**
     * Exception en cas d'échec du test
     */
    Supplier<Exception> sinon;

}
