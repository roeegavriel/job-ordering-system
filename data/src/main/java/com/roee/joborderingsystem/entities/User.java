package com.roee.joborderingsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @NotNull
    private String name;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Pattern(regexp = "^[\\w-\\+\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;

    private String mobile;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Business business;
}
