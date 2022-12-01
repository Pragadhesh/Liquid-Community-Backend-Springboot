package com.gmentor.spring.jpa.postgresql.controller;

import java.util.List;
import com.gmentor.spring.jpa.postgresql.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmentor.spring.jpa.postgresql.repository.MemberRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MemberController {

	@Autowired
	MemberRepository memberRepository;

	@GetMapping("/members/{sport}")
	public ResponseEntity<List<Member>> getMentorsBySport
		(@PathVariable("sport") String sport,
		@RequestParam(required = false) String country,
		@RequestParam(required = false) String state,
		 @RequestParam(required = false) String city
		) {
		List<Member> memberData;
		if(country == null && state == null && city == null) {
			memberData = memberRepository.findBySport(sport);
		} else if (state == null && city == null) {
			memberData = memberRepository.findBySportAndCountry(sport,country);
		}
		else if (city == null)
		{
			memberData = memberRepository.findBySportAndCountryAndState(sport,country,state);
		}
		else
		{
			 memberData = memberRepository.findBySportAndCountryAndStateAndCity(sport,country,state,city);
		}
		if (memberData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(memberData, HttpStatus.OK);
		}
	}


	@PostMapping("/login")
	public ResponseEntity<Object> validateLogin(
			@RequestParam(required = true) String email,
			@RequestParam(required = true) String password
	) {
		try {
			List<Member> _mentor = memberRepository.findByEmail(email);
			if (_mentor.isEmpty())
			{
				return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
			}
			else
			{
				Member member1 = _mentor.get(0);
				if (password.equals(member1.getPassword()))
				{
					return new ResponseEntity<>(member1,HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<>("Incorrect Password",HttpStatus.NOT_FOUND);
				}

			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Member> createMember(@RequestBody Member member) {
		try {
			Member _mentor = memberRepository.save(new Member(member.getName(),
					member.getSport(), member.getDescription(),
					member.getDiscordlink(), member.getGameprofile(),
					member.getCountry(), member.getState(), member.getCity(),
					member.isMentor(),member.getEmail(),member.getPassword()
			));
			return new ResponseEntity<>(_mentor, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
