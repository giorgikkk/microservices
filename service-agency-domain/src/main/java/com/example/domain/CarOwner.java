package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "car_owner")
public class CarOwner {

    @Id
    @Column(name = "car_owner_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_owner_id_gen")
    @SequenceGenerator(name = "car_owner_id_gen", sequenceName = "car_owner_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "car_owner_personal_No", unique = true, nullable = false, updatable = false)
    @Pattern(regexp = "\\d{11}")
    private String personalNo;

    @Column(name = "car_owner_first_name", nullable = false, length = 64)
    @NotBlank
    @Length(max = 64)
    private String firstName;

    @Column(name = "car_owner_last_name", nullable = false, length = 64)
    @NotBlank
    @Length(max = 64)
    private String lastName;

    @Column(name = "car_owner_birthday", nullable = false, updatable = false)
    @NotNull
    @Past
    private LocalDate birthday;

    @JsonIgnore
    @OneToMany(mappedBy = "carOwner")
    private Set<Car> cars;
}
