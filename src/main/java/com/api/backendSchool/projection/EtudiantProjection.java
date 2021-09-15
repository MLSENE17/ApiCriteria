package com.api.backendSchool.projection;

import com.api.backendSchool.model.Prof;

import java.time.LocalDate;
import java.util.Optional;

public interface EtudiantProjection {
    String getNom();
    String getPrenom();
    String getEmail();
    LocalDate getSigninDate();
    Prof getProf();
    String getNlp();
    int age();
    interface GroupeProjection{
        String getNom();
        Long getId();
    }
}
