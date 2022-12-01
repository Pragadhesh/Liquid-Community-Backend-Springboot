package com.gmentor.spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmentor.spring.jpa.postgresql.model.Mentor;
import com.gmentor.spring.jpa.postgresql.repository.MentorRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MentorController {

	@Autowired
	MentorRepository mentorRepository;

	@GetMapping("/mentors/{sport}")
	public ResponseEntity<List<Mentor>> getMentorsBySport
		(@PathVariable("sport") String sport,
		@RequestParam(required = false) String country,
		@RequestParam(required = false) String state,
		 @RequestParam(required = false) String city
		) {
		List<Mentor> mentorData;
		System.out.println("Reached here");
		if(country == null && state == null && city == null) {
			System.out.println("Reached get mentor by sport");
			mentorData = mentorRepository.findBySport(sport);
		} else if (state == null && city == null) {
			mentorData = mentorRepository.findBySportAndCountry(sport,country);
		}
		else if (city == null)
		{
			mentorData = mentorRepository.findBySportAndCountryAndState(sport,country,state);
		}
		else
		{
			 mentorData = mentorRepository.findBySportAndCountryAndStateAndCity(sport,country,state,city);
		}
		if (mentorData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(mentorData, HttpStatus.OK);
		}
	}


	@PostMapping("/mentors")
	public ResponseEntity<Mentor> createMentor(@RequestBody Mentor mentor) {
		try {
			Mentor _mentor = mentorRepository.save(new Mentor(mentor.getName(),
					mentor.getSport(), mentor.getDescription(),
					mentor.getDiscordlink(), mentor.getGameprofile(),
					mentor.getCountry(), mentor.getState(), mentor.getCity()
			));
			return new ResponseEntity<>(_mentor, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
