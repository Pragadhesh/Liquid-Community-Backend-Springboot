package com.gmentor.spring.jpa.postgresql.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sponsorshipapplied")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SponsorshipApplied {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sponsorship_applied_id_generator")
    @SequenceGenerator(name = "sponsorship_applied_id_generator", sequenceName = "sponsorship_applied_id_seq", allocationSize = 1)
    private long id;

    private String email;

    private String name;

    public SponsorshipApplied(String email, String name) {
        this.name = name;
        this.email = email;
    }
}
