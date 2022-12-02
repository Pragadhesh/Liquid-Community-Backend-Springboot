package com.gmentor.spring.jpa.postgresql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sponsorship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Sponsorship {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sponsorship_id_generator")
    @SequenceGenerator(name = "sponsorship_id_generator", sequenceName = "sponsorship_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Member admin;


    public Sponsorship(String name, String description,Member member) {
        this.name = name;
        this.description = description;
        this.admin = member;
    }

}
