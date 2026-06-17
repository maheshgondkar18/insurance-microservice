package com.insurance.insurance.mapper;

import com.insurance.insurance.Entity.InsurancePolicy;
import com.insurance.insurance.dto.PolicyAdminResponse;
import com.insurance.insurance.dto.PolicyAgentResponse;
import com.insurance.insurance.dto.PolicyUserResponse;
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

    public PolicyUserResponse toUserDto(InsurancePolicy p){
        return new PolicyUserResponse()
                .builder()
                .id(p.getId())
                .sumAssured(p.getSumAssured())
                .annualPremium(p.getAnnualPremium())
                .build();
    }

    public PolicyAgentResponse toAgentDto(InsurancePolicy p){
        return new PolicyAgentResponse()
                .builder()
                .id(p.getId())
                .userId(p.getUserId())
                .annualPremium(p.getAnnualPremium())
                .build();
    }
}