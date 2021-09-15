package com.api.backendSchool.repository;

import com.api.backendSchool.model.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class EtudiantCustomRepositoryImpl implements EtudiantCustomRepository{
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Etudiant> findByAgeAndPrenom(int age, String prenom) {
        //System.out.println("lammmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        CriteriaQuery cq=cb.createQuery(Etudiant.class);
        Root<Etudiant> etudiantRoot = cq.from(Etudiant.class);
        Predicate agePredicate = cb.equal(etudiantRoot.get("age"),age);
        Predicate prenomPredicate = cb.equal(etudiantRoot.get("prenom"),prenom);
        cq.where(agePredicate,prenomPredicate);
        TypedQuery<Etudiant> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
