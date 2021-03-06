package com.project.pethost.dbo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pethost.constant.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "request")
@NoArgsConstructor
public class OrderDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "status_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotNull
    private OrderStatusDbo status;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotNull
    private PetDbo pet;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @NotNull
    private String comments;

    @OneToOne(optional = false, mappedBy = "order")
    @JsonIgnore
    private ReviewDbo reviewDbo;
}
