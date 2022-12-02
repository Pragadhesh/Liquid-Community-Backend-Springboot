package com.gmentor.spring.jpa.postgresql.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SponsorshipStatusApplicant {

    private String name;
    private String description;
    private boolean admin_status;
    private boolean applied_status;
}
