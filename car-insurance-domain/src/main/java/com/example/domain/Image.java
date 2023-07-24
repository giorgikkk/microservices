package com.example.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "image")
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurer_id_gen")
    @SequenceGenerator(name = "insurer_id_gen", sequenceName = "insurer_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "image_base64_file", nullable = false)
    @NotBlank
    private String base64File;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "insured_car_id", nullable = false)
    @NotNull
    private InsuredCar car;
}
