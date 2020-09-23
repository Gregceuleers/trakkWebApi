package com.trakknamur.demo.models.enums;

public enum TypeParcours {
    TROU_18(18),
    TROU_9(9);

    int nbHoles;

    TypeParcours(int nbHoles) {
        this.nbHoles = nbHoles;
    }

    public int getNbHoles() {
        return nbHoles;
    }
}
