package com.gmentor.spring.jpa.postgresql.controller;

import com.gmentor.spring.jpa.postgresql.dao.Sponsorshipdao;
import com.gmentor.spring.jpa.postgresql.model.Member;
import com.gmentor.spring.jpa.postgresql.model.Sponsorship;
import com.gmentor.spring.jpa.postgresql.repository.MemberRepository;
import com.gmentor.spring.jpa.postgresql.repository.SponsorshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SponsorshipController {

    @Autowired
    SponsorshipRepository sponsorshipRepository;

    @Autowired
    MemberRepository memberRepository;

    //method to view all sponsorships
    @GetMapping("/sponsorships")
    public ResponseEntity<List<Sponsorship>> getAllSponsorships() {
        List<Sponsorship> sponsorship = sponsorshipRepository.findAll();
        if (sponsorship.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(sponsorship, HttpStatus.OK);
        }
    }




    // method to create sponsorship
    @PostMapping("/sponsorships")
    @Transactional
    public ResponseEntity<Sponsorship> createSponsorship(
            @RequestParam(required = true) String email,
            @RequestBody Sponsorshipdao sponsorshipdao
    ) {
        try {
            List<Member> member_details = memberRepository.findByEmail(email);
            if (member_details.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                Member member = member_details.get(0);
                Sponsorship sp = sponsorshipRepository.save(new Sponsorship(sponsorshipdao.getName(),sponsorshipdao.getDescription(),
                member));
                member.addSponsorshipadmin(sp);
                memberRepository.save(member);
                return new ResponseEntity<>(sp,HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
