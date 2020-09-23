package com.trakknamur.demo.repositories;

import com.trakknamur.demo.models.entities.Parcours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcoursRepository extends JpaRepository<Parcours, Long>, QuerydslPredicateExecutor<Parcours> {
}
