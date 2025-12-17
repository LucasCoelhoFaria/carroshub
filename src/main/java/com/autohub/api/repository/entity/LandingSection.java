package com.autohub.api.repository.entity;

import com.autohub.api.util.JsonMapConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "landing_section",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"business_id", "type"})
        }
)
public class LandingSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    private boolean enabled = true;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = JsonMapConverter.class)
    private Map<String, Object> content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    @JsonIgnore
    private Business business;
}
