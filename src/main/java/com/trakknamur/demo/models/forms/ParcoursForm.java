package com.trakknamur.demo.models.forms;

import com.trakknamur.demo.models.enums.TypeParcours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcoursForm {

    @NotBlank(message = "Le nom doit contenir du texte !")
    @NotNull(message = "Le nom ne peut être null !")
    @Size(min = 2, max = 50, message = "Le nom doit se composé d'au moins 2 caratères et au maximum 50 !")
    public String nom;

    @NotBlank(message = "L'email doit contenir du texte !")
    @NotNull(message = "L'email ne peut être null !")
    @Email(message = "L'email est invalide")
    public String email;

    public TypeParcours typeParcours;

    public List<TrouForm> trous;
}
