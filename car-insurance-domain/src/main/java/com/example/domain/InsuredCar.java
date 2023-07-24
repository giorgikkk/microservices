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
@Table(name = "insured_car")
public class InsuredCar {

    @Id
    @Column(name = "insured_car_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insured_car_id_gen")
    @SequenceGenerator(name = "insured_car_id_gen", sequenceName = "insured_car_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "insured_car_release_date", nullable = false, updatable = false)
    @NotNull
    @Past
    private LocalDate releaseDate;

    @Column(name = "insured_car_vin_code", nullable = false)
    @Pattern(regexp = "(?=.*\\d|=.*[A-Z])(?=.*[A-Z])[A-Z0-9]{17}")
    private String vinCode;

    @Column(name = "insured_car_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @Column(name = "insured_car_odometer_display", nullable = false)
    @Min(0)
    private long odometerDisplay;

    @Column(name = "insured_car_odometer_unit_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OdometerUnitType odometerUnitType;

    @Column(name = "insured_car_license_plate", nullable = false, unique = true)
    @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}")
    private String licensePlate;

    @Embedded
    private Insurance insurance;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "insurer_id", nullable = false)
    @NotNull
    private Insurer insurer;
}
