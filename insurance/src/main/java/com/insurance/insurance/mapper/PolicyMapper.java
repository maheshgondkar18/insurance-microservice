package com.insurance.insurance.mapper;

import com.insurance.insurance.Entity.InsurancePolicy;
import com.insurance.insurance.dto.PolicyAdminResponse;
import org.springframework.stereotype.Component;

@Component
public class PolicyMapper {

    public PolicyAdminResponse toAdminDto(InsurancePolicy ip){
        return new PolicyAdminResponse()
                .builder()
                .id(ip.getId())
                .agentId(ip.getAgentId())
                .annualPremium(ip.getAnnualPremium())
                .sumAssured(ip.getSumAssured())
                .build();


    }
}