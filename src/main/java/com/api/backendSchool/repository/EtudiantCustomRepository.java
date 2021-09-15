package com.api.backendSchool.repository;

import com.api.backendSchool.model.Etudiant;

import java.util.List;

public interface EtudiantCustomRepository {
    List<Etudiant> findByAgeAndPrenom(int age,String prenom);
}
