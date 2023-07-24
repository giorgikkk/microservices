package com.example.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
@Table(name = "owner")
public class Owner {

    @Id
    @Column(name = "owner_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_id_gen")
    @SequenceGenerator(name = "owner_id_gen", sequenceName = "owner_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "owner_personal_No", unique = true, nullable = false, updatable = false)
    @Pattern(regexp = "\\d{11}")
    private String personalNo;

    @Column(name = "owner_first_name", nullable = false, length = 64)
    @NotBlank
    @Length(max = 64)
    private String firstName;

    @Column(name = "owner_last_name", nullable = false, length = 64)
    @NotBlank
    @Length(max = 64)
    private String lastName;

    @Column(name = "owner_birthday", nullable = false, updatable = false)
    @NotNull
    @Past
    private LocalDate birthday;
}
