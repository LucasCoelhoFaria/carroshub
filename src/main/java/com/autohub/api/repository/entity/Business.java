package com.autohub.api.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "business",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_business_name", columnNames = "business_name"),
                @UniqueConstraint(name = "uq_google_token", columnNames = "google_token_hash")
        }
)
public class Business {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "business_name", nullable = false, length = 120)
    private String businessName;

    @Column(name = "google_token_hash", nullable = false, length = 255)
    private String googleTokenHash;

    @Column(length = 20)
    private String whatsapp;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<ServiceEntity> services;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<Faq> faqs;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
