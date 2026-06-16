package com.insurance.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyAgentResponse {

    private Long id;
    private Long userId;
    private double annualPremium;

}
