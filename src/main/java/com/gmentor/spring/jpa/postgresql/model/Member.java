package com.gmentor.spring.jpa.postgresql.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "email",unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name")
	private String name;

	@Column(name = "sport")
	private String sport;

	@Column(name = "description")
	private String description;

	@Column(name = "discordid")
	private String discordlink;

	@Column(name = "gameprofile")
	private String gameprofile;

	@Column(name = "country")
	private String country;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "mentor")
	private boolean mentor;

	public boolean isMentor() {
		return mentor;
	}

	public void setMentor(boolean mentor) {
		this.mentor = mentor;
	}

	public Member(String name, String sport, String description, String discordlink, String gameprofile, String country, String state, String city, boolean mentor,
	String email,String password) {
		this.name = name;
		this.sport = sport;
		this.description = description;
		this.discordlink = discordlink;
		this.gameprofile = gameprofile;
		this.country = country;
		this.state = state;
		this.city = city;
		this.mentor = mentor;
		this.email = email;
		this.password = password;
	}
}
