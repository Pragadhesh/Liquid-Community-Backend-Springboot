package com.gmentor.spring.jpa.postgresql.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "mentors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Mentor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

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

	public Mentor(String name, String sport, String description, String discordlink, String gameprofile, String country, String state, String city) {
		this.name = name;
		this.sport = sport;
		this.description = description;
		this.discordlink = discordlink;
		this.gameprofile = gameprofile;
		this.country = country;
		this.state = state;
		this.city = city;
	}
}
