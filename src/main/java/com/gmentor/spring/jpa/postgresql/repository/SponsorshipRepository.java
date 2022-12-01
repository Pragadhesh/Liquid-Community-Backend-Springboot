package com.gmentor.spring.jpa.postgresql.repository;

import com.gmentor.spring.jpa.postgresql.model.Sponsorship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorshipRepository extends JpaRepository<Sponsorship,Long> {

}
