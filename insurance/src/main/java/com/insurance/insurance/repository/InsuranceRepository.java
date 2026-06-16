package com.insurance.insurance.repository;

import com.insurance.insurance.Entity.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<InsurancePolicy, Long> {

    List<InsurancePolicy> findByUserId(Long userId);
    List<InsurancePolicy> findByAgentId(Long agentId);
    List<InsurancePolicy> findByUserIdIn(List<Long> userIds);

}
