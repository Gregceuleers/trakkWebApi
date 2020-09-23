package com.trakknamur.demo.models.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "trakk_user")
@Table(name = "trakk_user")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long idUser;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    @ElementCollection(fetch = FetchType.EAGER)
    List<String> roles = new ArrayList<>();

    @Column(name = "account_non_expired", columnDefinition = "BOOLEAN DEFAULT TRUE")
    boolean isAccountNonExpired;

    @Column(name = "account_non_locked" , columnDefinition = "BOOLEAN DEFAULT TRUE")
    boolean isAccountNonLocked;

    @Column(name = "credentials_non_expired", columnDefinition = "BOOLEAN DEFAULT TRUE")
    boolean isCredentialsNonExpired;

    @Column(name = "enabled", columnDefinition = "BOOLEAN DEFAULT TRUE")
    boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
