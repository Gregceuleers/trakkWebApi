package com.trakknamur.demo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    Long idUser;

    String username;

    String password;

    List<String> roles = new ArrayList<>();

    boolean isAccountNonExpired;

    boolean isAccountNonLocked;

    boolean isCredentialsNonExpired;

    boolean isEnabled;
}
