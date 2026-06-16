package com.insurance.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyUserResponse {
    private Long id;
    private double sumAssured;
    private double annualPremium;

}
