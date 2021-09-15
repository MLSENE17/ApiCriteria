package com.api.backendSchool.specification;

import com.api.backendSchool.model.Etudiant;
import com.api.backendSchool.model.Etudiant_;
import com.api.backendSchool.model.Prof;
import com.api.backendSchool.model.Prof_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

@Component
public class EtudiantSpecification {
    public static Specification<Etudiant> hasFirstName(String prenom){
        return ((root, criteriaQuery, criteriaBuilder) -> {

            return criteriaBuilder.like(root.get(Etudiant_.PRENOM),"%"+prenom+"%");
        });
    }
    public static Specification<Etudiant> hasProf(String prenom){
        return ((root, criteriaQuery, criteriaBuilder) -> {

            return criteriaBuilder.like(root.get(Etudiant_.PRENOM),"%"+prenom+"%");
        });
    }
    public static Specification<Etudiant> hasAge(String age){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Etudiant_.age),age);
        });
    }
    public static Specification<Prof> hasProf(int id){
        return  ((root,criteriaQuery,criteriaBuilder)->{
            Join<Prof,Etudiant> etudiant = root.join(Prof_.ETUDIANT, JoinType.LEFT);
            return criteriaBuilder.equal(etudiant.get(Etudiant_.PROF),id);
        });
    }
}
