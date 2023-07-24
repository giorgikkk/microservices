package com.example.domain;

import com.example.domain.enums.CarType;
import com.example.domain.enums.OdometerUnitType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_id_gen")
    @SequenceGenerator(name = "car_id_gen", sequenceName = "car_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "car_license_plate", nullable = false, unique = true)
    @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}")
    private String licensePlate;

    @Column(name = "car_vin_code", nullable = false)
    @Pattern(regexp = "(?=.*\\d|=.*[A-Z])(?=.*[A-Z])[A-Z0-9]{17}")
    private String vinCode;

    @Column(name = "car_release_date", nullable = false, updatable = false)
    @NotNull
    @Past
    private LocalDate releaseDate;

    @Column(name = "car_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @Column(name = "car_odometer_display", nullable = false)
    @Min(0)
    private long odometerDisplay;

    @Column(name = "car_odometer_unit_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OdometerUnitType odometerUnitType;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "car_owner_id")
    private CarOwner carOwner;
}
