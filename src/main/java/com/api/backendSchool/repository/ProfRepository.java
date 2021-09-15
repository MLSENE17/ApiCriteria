package com.api.backendSchool.repository;

import com.api.backendSchool.model.Etudiant;
import com.api.backendSchool.model.Prof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProfRepository extends JpaRepository<Prof,Long> , JpaSpecificationExecutor<Prof> {

}
