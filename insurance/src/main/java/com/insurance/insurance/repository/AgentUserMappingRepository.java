package com.insurance.insurance.repository;

import com.insurance.insurance.mapper.AgentUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentUserMappingRepository extends JpaRepository<AgentUserMapping,Long> {

    List<AgentUserMapping> findByAgentId(Long agentId);

    boolean existsByAgentIdAndUserId(Long agentId, Long userId);

}
