package com.gmentor.spring.jpa.postgresql.repository;

import java.util.List;

import com.gmentor.spring.jpa.postgresql.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findBySport(String sport);

    List<Member> findByEmail(String email);
    List<Member> findBySportAndCountry(String sport,String country);
    List<Member> findBySportAndCountryAndState(String sport,String country,String state);
    List<Member> findBySportAndCountryAndStateAndCity(String sport,String country,String state,String City);


}
