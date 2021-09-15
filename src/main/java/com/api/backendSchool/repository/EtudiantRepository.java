package com.api.backendSchool.repository;

import java.util.List;

import com.api.backendSchool.projection.EtudiantProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.backendSchool.model.Etudiant;
@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> ,JpaSpecificationExecutor<Etudiant> {
	@Query(value="select * from Etudiants u where u.prenom like %:keyword% or u.nom like %:keyword%", nativeQuery=true)
	List<Etudiant> findEtudiantsByKeyword(@Param("keyword") String keyword);
	@Query("Select e.nom as nom,e.prenom as prenom ,e.email as email," +
			"e.signinDate as signinDate,e.prof as prof,e.age as age from Etudiant e")
	List<EtudiantProjection> getChampSelected();
}
