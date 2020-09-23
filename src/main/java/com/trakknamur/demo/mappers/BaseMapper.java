package com.trakknamur.demo.mappers;

public interface BaseMapper<TENTITY, TFORM, TDTO> {
    TENTITY fromFormToEntity(TFORM form);
    TDTO toDto(TENTITY entity);
    TENTITY toEntity(TDTO dto);
}
