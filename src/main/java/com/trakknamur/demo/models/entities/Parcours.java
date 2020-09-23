package com.trakknamur.demo.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trakknamur.demo.models.enums.TypeParcours;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;


@Entity(name = "parcours")
@Table(name = "parcours")
@Data
public class Parcours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @AccessType(AccessType.Type.PROPERTY)
    long idParcours;

    @Column(name = "nom", length = 50, nullable = false)
    @NotBlank(message = "Le nom doit contenir du texte !")
    String nom;

    @Column(name = "email")
    String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_parcours")
    TypeParcours typeParcours;

    @CreationTimestamp
    @Column(name = "date_creation", columnDefinition = "DATETIME(6)")
    Instant dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification", columnDefinition = "DATETIME(6)")
    Instant dateModification;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "parcours")
    @JsonManagedReference
    List<Trou> trous;

}
