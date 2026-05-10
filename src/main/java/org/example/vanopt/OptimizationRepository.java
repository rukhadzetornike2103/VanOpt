package org.example.vanopt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptimizationRepository extends JpaRepository<OptimizationResponse, String> {

}