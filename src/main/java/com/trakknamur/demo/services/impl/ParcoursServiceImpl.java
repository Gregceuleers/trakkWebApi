package com.trakknamur.demo.services.impl;

import com.querydsl.core.BooleanBuilder;
import com.trakknamur.demo.mappers.ParcoursMapper;
import com.trakknamur.demo.mappers.WebApiMapper;
import com.trakknamur.demo.models.dtos.ParcoursDTO;
import com.trakknamur.demo.models.entities.Parcours;
import com.trakknamur.demo.models.entities.QParcours;
import com.trakknamur.demo.models.forms.ParcoursForm;
import com.trakknamur.demo.repositories.ParcoursRepository;
import com.trakknamur.demo.services.ParcoursService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@PreAuthorize("hasAuthority('USER')")
public class ParcoursServiceImpl implements ParcoursService {

    private final ParcoursRepository parcoursRepository;

    private final ParcoursMapper parcoursMapper;

    private final WebApiMapper webApiMapper;

    public ParcoursServiceImpl(ParcoursRepository parcoursRepository, ParcoursMapper parcoursMapper, WebApiMapper webApiMapper) {
        this.parcoursRepository = parcoursRepository;
        this.parcoursMapper = parcoursMapper;
        this.webApiMapper = webApiMapper;
    }

    @Override
    public List<ParcoursDTO> getAll() {

        return this.parcoursRepository.findAll()
                .stream()
                .map(webApiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void insert(ParcoursForm p) {

        this.parcoursRepository.save(this.webApiMapper.fromFormToEntity(p));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ParcoursDTO insertWithReturnValue(ParcoursForm p) {
        return
                this.webApiMapper.toDto(this.parcoursRepository.save(this.webApiMapper.fromFormToEntity(p)));
    }

    @Override
    public ParcoursDTO getOne(long id) {
        return this.webApiMapper.toDto(this.parcoursRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parcours non trouvé")));
    }

    @Override
    public String delete(long id) {

        Parcours parcoursDelete = this.webApiMapper.toEntity(getOne(id));

        this.parcoursRepository.delete(parcoursDelete);

        return parcoursDelete.getNom();


    }

    @Override
    public ParcoursDTO update(long id, ParcoursForm form) {

        Parcours parcoursUpdate = this.webApiMapper.toEntity(getOne(id));

        parcoursUpdate.setNom(form.getNom());
        parcoursUpdate.setTypeParcours(form.getTypeParcours());
        parcoursUpdate.setEmail(form.getEmail());

        this.parcoursRepository.save(parcoursUpdate);

        return this.webApiMapper.toDto(parcoursUpdate);
    }

    @Override
    public List<ParcoursDTO> search(ParcoursForm form) {

        BooleanBuilder predicate = new BooleanBuilder();

        QParcours qParcours = QParcours.parcours;

        if (form.getNom() != null) {
            predicate.and(qParcours.nom.containsIgnoreCase(form.getNom()));
        }

        if (form.getEmail() != null) {
            predicate.and(qParcours.email.containsIgnoreCase(form.getEmail()));
        }

        if (form.getTypeParcours() != null) {
            predicate.and(qParcours.typeParcours.eq(form.getTypeParcours()));
        }


        Iterable<Parcours> result = this.parcoursRepository.findAll(predicate);

        return StreamSupport.stream(result.spliterator(), false)
                .map(webApiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ParcoursDTO> getAllWithPagination(int page, int size) {

        List<ParcoursDTO> result = this.parcoursRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(webApiMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(result);
    }
}
