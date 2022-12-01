package com.gmentor.spring.jpa.postgresql.repository;

import java.util.List;

import com.gmentor.spring.jpa.postgresql.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

    List<Mentor> findBySport(String sport);
    List<Mentor> findBySportAndCountry(String sport,String country);
    List<Mentor> findBySportAndCountryAndState(String sport,String country,String state);
    List<Mentor> findBySportAndCountryAndStateAndCity(String sport,String country,String state,String City);


}
