package com.project.pethost.dbo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "pet")
@AllArgsConstructor
@NoArgsConstructor
public class PetDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @OneToOne(targetEntity = AnimalCategoryDbo.class)
    private AnimalCategoryDbo category;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private UserDbo owner;

    @ManyToOne
    @JoinColumn(name = "keeper_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserDbo keeper;

    private String description;

    private String avatarUrl;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;
}
