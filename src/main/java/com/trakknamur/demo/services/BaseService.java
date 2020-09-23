package com.trakknamur.demo.services;

import java.util.List;

public interface BaseService<TDTO, TFORM, TID> {

    List<TDTO> getAll();

    TDTO getOne(TID id);

    void insert(TFORM form);

    void delete(TID id);

    TDTO update(TFORM form, TID id);


}
