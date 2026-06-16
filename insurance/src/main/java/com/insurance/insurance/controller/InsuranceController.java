package com.insurance.insurance.controller;

import com.insurance.insurance.Entity.InsurancePolicy;
import com.insurance.insurance.dto.InsuranceRequest;
import com.insurance.insurance.dto.InsuranceResponse;
import com.insurance.insurance.service.InsuranceCalculationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insurance")
@SecurityRequirement(name = "bearerAuth")
public class InsuranceController {

    @Autowired
    private InsuranceCalculationService insuranceCalculationService;

    @PostMapping("/apply")
    public InsurancePolicy apply(@RequestBody InsuranceRequest request){
        return insuranceCalculationService.calculateAndSave(request);
    }

    @GetMapping("/users/{userId}")
    public List<InsurancePolicy> getUserPolicies(@PathVariable Long userId,
                                                 @RequestHeader("X-User-Id") Long requesterId,
                                                 @RequestHeader("X-User-Role") String role){
        return insuranceCalculationService.getPoliciesByUser(userId,requesterId,role);
    }

    @GetMapping("/all")
    public  List<InsurancePolicy> getALLPolicies(@RequestHeader("X-User-Id") Long requesterId,
                                                 @RequestHeader("X-User-Role") String role){
        return insuranceCalculationService.getAllPolicies(requesterId,role);
    }

}

