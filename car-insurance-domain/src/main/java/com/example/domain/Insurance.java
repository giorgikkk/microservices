package com.example.domain;

import com.example.domain.enums.InsuranceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
public class Insurance {

    @Column(name = "insured_car_insurance_full_amount", nullable = false)
    @NotNull
    private BigDecimal insuranceAmount;

    @Column(name = "insured_car_insurance_pension", insertable = false)
    private BigDecimal insurancePension;

    @Column(name = "insured_car_insurance_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private InsuranceType insuranceType;

    @Column(name = "insured_car_franchise_amount", nullable = false)
    @NotNull
    private BigDecimal franchiseAmount;
}
