package com.gmentor.spring.jpa.postgresql.repository;

import com.gmentor.spring.jpa.postgresql.model.Sponsorship;
import com.gmentor.spring.jpa.postgresql.model.SponsorshipApplied;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SponsorshipAppliedRepository extends JpaRepository<SponsorshipApplied,Long> {

    List<Sponsorship> findByName(String name);

    List<Sponsorship> findByEmail(String email);


}
