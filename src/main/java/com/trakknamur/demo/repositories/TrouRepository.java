package com.trakknamur.demo.repositories;

import com.trakknamur.demo.models.entities.Trou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrouRepository extends JpaRepository<Trou, Long> {
}
