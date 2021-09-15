package com.api.backendSchool.controller;

import com.api.backendSchool.model.Prof;
import com.api.backendSchool.repository.ProfRepository;
import com.api.backendSchool.specification.EtudiantSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("prof")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProfController {
    @Autowired
    private ProfRepository profRepository;
  @GetMapping("/all")
  public List<Prof> getAll(@RequestParam int id){
      return  profRepository.findAll(EtudiantSpecification.hasProf(id));
  }
}
