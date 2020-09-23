package com.trakknamur.demo.services;


import com.trakknamur.demo.models.dtos.ParcoursDTO;
import com.trakknamur.demo.models.forms.ParcoursForm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ParcoursService {

    List<ParcoursDTO> getAll();

    void insert(ParcoursForm p);

    ParcoursDTO insertWithReturnValue(ParcoursForm p);

    ParcoursDTO getOne(long id);

    String delete(long id);

    ParcoursDTO update(long id, ParcoursForm form);

    List<ParcoursDTO> search(ParcoursForm form);

    Page<ParcoursDTO> getAllWithPagination(int page, int size);
}
