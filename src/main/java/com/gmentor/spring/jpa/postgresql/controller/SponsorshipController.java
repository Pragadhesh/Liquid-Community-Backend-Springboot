package com.gmentor.spring.jpa.postgresql.controller;

import com.gmentor.spring.jpa.postgresql.dao.Sponsorshipdao;
import com.gmentor.spring.jpa.postgresql.dto.SponsorshipStatusApplicant;
import com.gmentor.spring.jpa.postgresql.model.Member;
import com.gmentor.spring.jpa.postgresql.model.Sponsorship;
import com.gmentor.spring.jpa.postgresql.model.SponsorshipApplied;
import com.gmentor.spring.jpa.postgresql.repository.MemberRepository;
import com.gmentor.spring.jpa.postgresql.repository.SponsorshipAppliedRepository;
import com.gmentor.spring.jpa.postgresql.repository.SponsorshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class SponsorshipController {

    @Autowired
    SponsorshipRepository sponsorshipRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SponsorshipAppliedRepository sponsorshipAppliedRepository;

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

    // method to apply for sponsorship
    @PostMapping("/apply")
    @Transactional
    public ResponseEntity<SponsorshipApplied> applySponsorship(
            @RequestParam(required = true) String email,
            @RequestParam(required = true) String name
    ) {
        try {
            List<Member> member_details = memberRepository.findByEmail(email);
            List<Sponsorship> sponsorship_details = sponsorshipRepository.findByName(name);
            if (member_details.isEmpty() || sponsorship_details.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                Member member = member_details.get(0);
                Sponsorship sponsorship = sponsorship_details.get(0);
                SponsorshipApplied sponsorshipApplied =
                        sponsorshipAppliedRepository.save(
                                new SponsorshipApplied(member.getEmail(),sponsorship.getName())
                        );
                return new ResponseEntity<>(sponsorshipApplied, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //method to list applicants applied for sponsorship
    @GetMapping("/sponsorships/{sponsorshipname}")
    public ResponseEntity<List<Member>> getAllMembersofSponsorship(
            @PathVariable(required = true) String sponsorshipname
    ) {
        try {
            List<SponsorshipApplied> sponsorshipApplied = sponsorshipAppliedRepository.findByName(sponsorshipname);
            List<Member> members = new ArrayList<>();
            sponsorshipApplied.stream().forEach(
                    sponsorshipApplied1 -> {
                        members.add(memberRepository.findByEmail(sponsorshipApplied1.getEmail()).get(0));
                    }
            );
            return new ResponseEntity<>(members,HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // method to list sponsorship details of applicant
    @GetMapping("/sponsorships/applicant/{email}")
    public ResponseEntity<List<SponsorshipStatusApplicant>> getAllSponsorshipsOfApplicant(
            @PathVariable(required = true) String email
    )
    {
        try {
            List<Sponsorship> sponsorship = sponsorshipRepository.findAll();
            List<Sponsorship> admins = memberRepository.findByEmail(email).get(0).getAdmin();
            List<SponsorshipApplied> sponsorshipApplied = sponsorshipAppliedRepository.findByEmail(email);
            List<SponsorshipStatusApplicant> status = new ArrayList<>();

            for (Sponsorship sp: sponsorship)
            {
                boolean admin = false;
                boolean applied = false;
                for (Sponsorship ad: admins)
                {
                    if(sp.getName().equals(ad.getName()))
                    {
                        status.add(
                                new SponsorshipStatusApplicant(
                                      sp.getName(),sp.getDescription(),true,false
                                )
                        );
                        admin = true;
                        break;
                    }
                }
                for (SponsorshipApplied ap: sponsorshipApplied)
                {
                    if(ap.getName().equals(sp.getName()))
                    {
                        status.add(
                                new SponsorshipStatusApplicant(
                                        sp.getName(),sp.getDescription(),false,true
                                )
                        );
                        applied = true;
                        break;
                    }
                }
                if(!applied && !admin )
                {
                    status.add(
                            new SponsorshipStatusApplicant(
                                    sp.getName(),sp.getDescription(),false,false
                            )
                    );
                }
            }
            return new ResponseEntity<>(status,HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
