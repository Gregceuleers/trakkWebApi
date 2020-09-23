package com.trakknamur.demo.services.impl;

import com.trakknamur.demo.exceptions.models.RuleGolfException;
import com.trakknamur.demo.mappers.TrouMapper;
import com.trakknamur.demo.mappers.WebApiMapper;
import com.trakknamur.demo.models.dtos.TrouDTO;
import com.trakknamur.demo.models.entities.Trou;
import com.trakknamur.demo.models.forms.TrouForm;
import com.trakknamur.demo.repositories.ParcoursRepository;
import com.trakknamur.demo.repositories.TrouRepository;
import com.trakknamur.demo.services.TrouService;
import com.trakknamur.demo.util.GolfUtils;
import com.trakknamur.demo.util.RegleMetier;
import com.trakknamur.demo.util.Validateur;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TrouServiceImpl implements TrouService {

    private final TrouRepository trouRepository;

    private final ParcoursRepository parcoursRepository;

    private final GolfUtils golfUtils;

    private final TrouMapper trouMapper;

    private final WebApiMapper webApiMapper;

    public TrouServiceImpl(TrouRepository trouRepository, ParcoursRepository parcoursRepository, GolfUtils golfUtils, TrouMapper trouMapper, WebApiMapper webApiMapper) {
        this.trouRepository = trouRepository;
        this.parcoursRepository = parcoursRepository;
        this.golfUtils = golfUtils;
        this.trouMapper = trouMapper;
        this.webApiMapper = webApiMapper;
    }

    @Override
    public List<TrouDTO> getAll() {
        return this.trouRepository.findAll().stream()
                .map(webApiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void insert(TrouForm t) throws Exception {

        Validateur.validerReglesMetier(
                RegleMetier.builder()
                        .si(t.getIdParcours() != null)
                        .regle(() -> this.parcoursRepository.existsById(t.getIdParcours()))
                        .doitRetourner(true)
                        .sinon(() -> new NoSuchElementException("Le parcours avec l'id "+ t.getIdParcours() +" n'existe pas"))
                        .build(),
                // Règle qui vérifie
                // SI j'ai un PAR_3 la distance doit être comprise entre 10m et 180m
                // SI j'ai un PAR_4 la distance doit être comprise entre 10m et 350m
                // SI j'ai un PAR_5 la distance doit être comprise entre 10m et 600m
                RegleMetier.builder()
                        .regle(() -> t.getDistance() >= t.getTypeTrou().getMin() && t.getDistance() <= t.getTypeTrou().getMax())
                        .doitRetourner(true)
                        .sinon(() -> new RuleGolfException("La distance de "+ t.getDistance()+" pour le type de PAR "+ t.getTypeTrou() +" ne correspond pas aux valeurs min ("+t.getTypeTrou().getMin()+") et max ("+t.getTypeTrou().getMax()+")"))
                        .build(),
                // Règle qui vérifie
                // SI le parcours est un TROU_18 -> on ne peut affecter plus de 18 trous
                // SI le parcours est un TROU_9 -> on ne peut affecter plus de 9 trous
                RegleMetier.builder()
                        .regle(() -> this.golfUtils.checkIfNbHolesNotExceededTypeParcours(t))
                        .doitRetourner(true)
                        .sinon(() -> new RuleGolfException("On ne peut plus ajouter de trous au parcours"))
                        .build()
        );





        this.trouRepository.save(this.webApiMapper.fromFormToEntity(t));
    }

    @Override
    public TrouDTO insertWithReturnValue(TrouForm t) throws Exception {

        Validateur.validerReglesMetier(
                RegleMetier.builder()
                        .si(t.getIdParcours() != null)
                        .regle(() -> this.parcoursRepository.existsById(t.getIdParcours()))
                        .doitRetourner(true)
                        .sinon(() -> new NoSuchElementException("Le parcours avec l'id "+ t.getIdParcours() +" n'existe pas"))
                        .build(),
                // Règle qui vérifie
                // SI j'ai un PAR_3 la distance doit être comprise entre 10m et 180m
                // SI j'ai un PAR_4 la distance doit être comprise entre 10m et 350m
                // SI j'ai un PAR_5 la distance doit être comprise entre 10m et 600m
                RegleMetier.builder()
                        .regle(() -> t.getDistance() >= t.getTypeTrou().getMin() && t.getDistance() <= t.getTypeTrou().getMax())
                        .doitRetourner(true)
                        .sinon(() -> new RuleGolfException("La distance de "+ t.getDistance()+" pour le type de PAR "+ t.getTypeTrou() +" ne correspond pas aux valeurs min ("+t.getTypeTrou().getMin()+") et max ("+t.getTypeTrou().getMax()+")"))
                        .build(),
                // Règle qui vérifie
                // SI le parcours est un TROU_18 -> on ne peut affecter plus de 18 trous
                // SI le parcours est un TROU_9 -> on ne peut affecter plus de 9 trous
                RegleMetier.builder()
                        .regle(() -> this.golfUtils.checkIfNbHolesNotExceededTypeParcours(t))
                        .doitRetourner(true)
                        .sinon(() -> new RuleGolfException("Le parcours de type ne peut plus ajouter de trous"))
                        .build()
        );


        return
                this.webApiMapper.toDto(this.trouRepository.save(this.webApiMapper.fromFormToEntity(t)));
    }

    @Override
    public TrouDTO getOne(long id) {
        return this.webApiMapper.toDto(this.trouRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trou non trouvé")));
    }

    @Override
    public String delete(long id) {

        Trou trouDelete = this.webApiMapper.toEntity(getOne(id));

        this.trouRepository.delete(trouDelete);


        return String.valueOf(trouDelete.getIdTrou());
    }

    @Override
    public TrouDTO update(long id, TrouForm form) {

        Trou trouUpdate = this.webApiMapper.toEntity(getOne(id));

        trouUpdate.setDistance(form.getDistance());
        trouUpdate.setTypeTrou(form.getTypeTrou());

        this.trouRepository.save(trouUpdate);

        return this.webApiMapper.toDto(trouUpdate);
    }

    @Override
    public TrouDTO partialUpdate(Map<String, Object> updates, Long id) throws IllegalAccessException {

        Trou trouUpdate = this.webApiMapper.toEntity(getOne(id));

        Class<?> clazz = trouUpdate.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            Field field = Arrays.stream(fields)
                    .filter(f -> f.getName().equals(entry.getKey()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Champ de la classe non trouvé"));
            // Comme mon champ est en accès PRIVATE, je ne peux sans changer l'accessibilité modifier par .set mon champ
            field.setAccessible(true);
            field.set(trouUpdate, entry.getValue());
        }

        this.trouRepository.save(trouUpdate);

        return this.webApiMapper.toDto(trouUpdate);
    }
}
