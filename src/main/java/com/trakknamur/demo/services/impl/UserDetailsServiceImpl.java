package com.trakknamur.demo.services.impl;


import com.trakknamur.demo.configs.PasswordEncoderConfig;
import com.trakknamur.demo.exceptions.models.PasswordNotValidException;
import com.trakknamur.demo.mappers.WebApiMapper;
import com.trakknamur.demo.models.dtos.UserDTO;
import com.trakknamur.demo.models.entities.User;
import com.trakknamur.demo.models.forms.UserForm;
import com.trakknamur.demo.repositories.UserRepository;
import com.trakknamur.demo.services.BaseService;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService, BaseService<UserDTO, UserForm, Long> {

    private final UserRepository userRepository;

    private final WebApiMapper webApiMapper;

    private final PasswordEncoderConfig passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, WebApiMapper webApiMapper, PasswordEncoderConfig passwordEncoder) {
        this.userRepository = userRepository;
        this.webApiMapper = webApiMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur avec le nom : " + s + " n'existe pas"));
    }

//    public String insert(User u) {
//        User user = this.userRepository.save(u);
//        return user.getUsername();
//    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDTO> getAll() {
        return this.userRepository.findAll()
                .stream()
                .map(webApiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO getOne(Long id) {
        return this.webApiMapper.toDto(this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé")));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean insert(UserForm form) {

        User uBeforeInserted = this.webApiMapper.fromFormToEntity(form);
        uBeforeInserted.setAccountNonExpired(true);
        uBeforeInserted.setAccountNonLocked(true);
        uBeforeInserted.setCredentialsNonExpired(true);
        uBeforeInserted.setEnabled(true);

        // Cryptage du password
        uBeforeInserted.setPassword(this.passwordEncoder.getPasswordEncoder().encode(uBeforeInserted.getPassword()));

        User uInserted = this.userRepository.save(uBeforeInserted);

        return uInserted.getIdUser() > 0;

    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean delete(Long id) {

        User userDelete = this.webApiMapper.toEntity(getOne(id));

        this.userRepository.delete(userDelete);

        return !this.userRepository.existsById(userDelete.getIdUser());
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO update(UserForm form, Long id) {

        User userUpdated = this.webApiMapper.toEntity(getOne(id));

        userUpdated.setUsername(form.getUsername());
        userUpdated.setPassword(this.passwordEncoder.getPasswordEncoder().encode(form.getPassword()));
        userUpdated.setRoles(form.getRoles());

        return this.webApiMapper.toDto(this.userRepository.save(userUpdated));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO updateAccess(Map<String, Object> updates, Long id) throws IllegalAccessException {

        User userUpdateAccess = this.webApiMapper.toEntity(getOne(id));

        Class<?> clazz = userUpdateAccess.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for(Map.Entry<String, Object> entry : updates.entrySet()) {
            Field field = Arrays.stream(fields)
                    .filter(f -> f.getName().equals(entry.getKey()))
                    .findFirst()
                    .orElseThrow(()->new IllegalArgumentException("Champ de la classe non trouvé"));
            field.setAccessible(true);
            field.set(userUpdateAccess, entry.getValue());
        }

        this.userRepository.save(userUpdateAccess);

        return this.webApiMapper.toDto(userUpdateAccess);

    }

    public UserDTO checkAuthenticate(UserForm form) throws PasswordNotValidException {

        User user = (User) this.loadUserByUsername(form.getUsername());



        if (!passwordEncoder.getPasswordEncoder().matches(form.getPassword(), user.getPassword())) {
            throw new PasswordNotValidException();
        }

        return this.webApiMapper.toDto(user);


    }

    public void signUp(UserForm user) {

        User uBeforeInserted = this.webApiMapper.fromFormToEntity(user);
        uBeforeInserted.setAccountNonExpired(true);
        uBeforeInserted.setAccountNonLocked(true);
        uBeforeInserted.setCredentialsNonExpired(true);
        uBeforeInserted.setEnabled(true);

        // Cryptage du password
        uBeforeInserted.setPassword(this.passwordEncoder.getPasswordEncoder().encode(uBeforeInserted.getPassword()));

        uBeforeInserted.setRoles(user.getRoles());

        this.userRepository.save(uBeforeInserted);

    }
}
