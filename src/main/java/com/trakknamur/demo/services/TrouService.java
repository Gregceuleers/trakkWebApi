package com.trakknamur.demo.services;

import com.trakknamur.demo.models.dtos.TrouDTO;
import com.trakknamur.demo.models.forms.TrouForm;

import java.util.List;
import java.util.Map;

public interface TrouService {

    List<TrouDTO> getAll();

    void insert(TrouForm t) throws Exception;

    TrouDTO insertWithReturnValue(TrouForm t) throws Exception;

    TrouDTO getOne(long id);

    String delete(long id);

    TrouDTO update(long id, TrouForm form);

    TrouDTO partialUpdate(Map<String, Object> updates, Long id) throws IllegalAccessException;
}
