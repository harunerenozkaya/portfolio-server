package com.harunerenozkaya.portfolio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personal_information")
public class PersonalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false, length = 1000)
    private String summary;

    @Column(nullable = false, length = 2000)
    private String biography;

    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "personal_information_id"))
    @Column(name = "skill")
    private List<String> skills;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "personal_information_id")
    private List<SocialMediaLink> socialMediaLinks;

    @Column(nullable = false)
    private String personalImageUrl;
}
