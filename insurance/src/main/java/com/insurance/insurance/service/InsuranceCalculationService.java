package com.insurance.insurance.service;

import com.insurance.insurance.Entity.InsurancePolicy;
import com.insurance.insurance.dto.InsuranceRequest;
import com.insurance.insurance.dto.InsuranceResponse;
import com.insurance.insurance.mapper.AgentUserMapping;
import com.insurance.insurance.repository.AgentUserMappingRepository;
import com.insurance.insurance.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InsuranceCalculationService {

    @Autowired
    private InsuranceRepository repository;

    @Autowired
    private AgentUserMappingRepository agentUserMappingRepository;

    public InsurancePolicy calculateAndSave(InsuranceRequest request) {

        // ✅ Eligibility checks
        if (request.getAge() < 18 || request.getAge() > 60) {
            throw new RuntimeException("Age not eligible");
        }

        if (request.getAnnualIncome() < 200000) {
            throw new RuntimeException("Income too low");
        }

        if (request.getAge() + request.getTermYears() > 65) {
            throw new RuntimeException("Term exceeds allowed age");
        }

        // ✅ Sum assured
        double sumAssured = request.getAnnualIncome() * 10;

        // ✅ Base rate
        double rate;
        if (request.getAge() <= 30) rate = 500;
        else if (request.getAge() <= 45) rate = 800;
        else rate = 1200;

        double smokerFactor = request.isSmoker() ? 1.2 : 1.0;

        double annualPremium =
                (sumAssured / 100000) * rate * smokerFactor;

        double monthlyPremium = annualPremium / 12;

        // ✅ Build entity
        InsurancePolicy policy = new InsurancePolicy();
        policy.setUserId(request.getUserId());
        policy.setAgentId((request.getAgentId()));
        policy.setAge(request.getAge());
        policy.setFirstName(request.getFirstName());
        policy.setLastName(request.getLastName());
        policy.setTermYears(request.getTermYears());
        policy.setSumAssured(sumAssured);
        policy.setAnnualPremium(annualPremium);
        policy.setMonthlyPremium(monthlyPremium);
        policy.setSmoker(request.isSmoker());
        policy.setStatus("ACTIVE");
        policy.setCreatedAt(LocalDateTime.now());
        InsurancePolicy savedPolicy = repository.save(policy);

        boolean exists = agentUserMappingRepository
                .existsByAgentIdAndUserId(
                        request.getAgentId(),
                        request.getUserId()
                );

        if (!exists) {
            AgentUserMapping mapping = new AgentUserMapping();
            mapping.setAgentId(request.getAgentId());
            mapping.setUserId(request.getUserId());
            mapping.setAssignedAt(LocalDateTime.now());

            agentUserMappingRepository.save(mapping);
        }
        return savedPolicy;
    }

    public List<InsurancePolicy> getPoliciesByUser(Long userId,Long requesterId, String role){
        if(role.equals("ADMIN")){
            return repository.findByUserId(userId);
        }
        if(role.equals("USER")){
            if(!userId.equals(requesterId)){
                throw new RuntimeException("Access Denied: User can only view it own data");
            }
            return repository.findByUserId(userId);
        }
        if(role.equals("AGENT")){
            boolean allowed = agentUserMappingRepository.existsByAgentIdAndUserId(requesterId,userId);

            if(!allowed){
                throw new RuntimeException("Access Denied: Agent not assigned to this user");
            }
            return repository.findByUserId(userId);
        }
        throw new RuntimeException("Invalid role");
    }
    public List<InsurancePolicy> getPoliciesByAgent(Long agentId){
        return  repository.findByAgentId(agentId);
    }

    public List<InsurancePolicy> getAllPolicies(Long requesterId, String role){

        System.out.println("INSIDE getAllPolicies()");
        System.out.println("ROLE = " + role);

        if (role.equals("ADMIN")) {
            return repository.findAll()
                    .stream()
                    .map(this:: to)
        }
        if(role.equals("USER")){
            return repository.findByUserId(requesterId);
        }
        if(role.equals("AGENT")){
            List<AgentUserMapping> agentUserMappings= agentUserMappingRepository.findByAgentId(requesterId);

            List<Long> userIds = agentUserMappings.stream().map(AgentUserMapping:: getUserId)
                    .toList();

            return repository.findByUserIdIn(userIds);
        }

        throw new RuntimeException("Invalid role");
    }
}
