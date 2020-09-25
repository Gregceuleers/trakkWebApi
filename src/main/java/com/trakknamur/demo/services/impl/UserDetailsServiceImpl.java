package com.trakknamur.demo.services.impl;


import com.trakknamur.demo.configs.PasswordEncoderConfig;
import com.trakknamur.demo.mappers.WebApiMapper;
import com.trakknamur.demo.models.dtos.UserDTO;
import com.trakknamur.demo.models.entities.User;
import com.trakknamur.demo.models.forms.UserForm;
import com.trakknamur.demo.repositories.UserRepository;
import com.trakknamur.demo.services.BaseService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public List<UserDTO> getAll() {
        return this.userRepository.findAll()
                .stream()
                .map(webApiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getOne(Long id) {
        return null;
    }

    @Override
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
    public boolean delete(Long id) {
        throw new NotYetImplementedException();
    }

    @Override
    public UserDTO update(UserForm form, Long id) {
        return null;
    }
}
