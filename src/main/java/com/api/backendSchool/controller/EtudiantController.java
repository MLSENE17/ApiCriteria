package com.api.backendSchool.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.api.backendSchool.projection.EtudiantProjection;
import com.api.backendSchool.repository.EtudiantRepository;
import com.api.backendSchool.specification.EtudiantSpecification;
import com.api.backendSchool.specification.EtudiantSpecificationsBuilder;
import com.api.backendSchool.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.api.backendSchool.exception.ResourceNotFoundException;
import com.api.backendSchool.model.Etudiant;
import com.api.backendSchool.model.SearchEtudiant;
import com.api.backendSchool.service.EtudiantService;

import javax.transaction.Transactional;

@RestController
@RequestMapping("etudiant")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EtudiantController {
	@Autowired
	private EtudiantService etudiantService;
	@Autowired
	private EtudiantRepository etudiantRepository;
	@GetMapping("/projection")
	public List<EtudiantProjection> getProjection()
	{
		return etudiantRepository.getChampSelected();
	}
	@PostMapping("/fetch")
	public List<Etudiant> getAgeAndPrenom(@RequestBody List<SearchCriteria> searchCriteria){
		//return etudiantRepository.findAll(Specification.where(EtudiantSpecification.hasFirstName(prenom).and(EtudiantSpecification.hasFirstName(prenom))));
		//System.out.println(searchCriteria);
		EtudiantSpecificationsBuilder builder = new EtudiantSpecificationsBuilder();
		for(SearchCriteria sc:searchCriteria){
			builder.with(sc.getKey(),sc.getOperation(),sc.getValue());
		}
		Specification<Etudiant> spec = builder.build();
		return etudiantRepository.findAll(spec);
	}
	@PostMapping("/search")
	public List<Etudiant> searchEtudiant(@RequestBody SearchEtudiant search)
	{
		System.out.println(search.getKeyword());
		return this.etudiantService.searchEtudiant(search.getKeyword());
	}
	@GetMapping("/total")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<Map<String,Long>> TotalAll(){
		return  ResponseEntity.ok(this.etudiantService.total());
	}
	@GetMapping("/all")
	public List<Etudiant> getAll(){
		return this.etudiantService.getAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity getOne(@PathVariable Long id) {
		Etudiant et = this.etudiantService.getOne(id)
				.orElseThrow(
						()-> new ResourceNotFoundException("Etudiant non trouver")
						);
	    return ResponseEntity.ok(et);
	}
	@PostMapping("/create")
	public Etudiant saveOne(@RequestBody Etudiant et) {
		Etudiant ets = this.etudiantService.save(et);
		return ets;
	}
	@PutMapping("/edit/{id}")
	public ResponseEntity<Etudiant> saveOne(@PathVariable Long id,@RequestBody Etudiant ets)
	{
		Etudiant et = this.etudiantService.getOne(id)
				.orElseThrow(
						()-> new ResourceNotFoundException("Probleme pour la modification")
						);
	    et.setNom(ets.getNom());
	    et.setEmail(ets.getEmail());
	    et.setPrenom(ets.getPrenom());
	    et.setClasse(ets.getClasse());
	    Etudiant etss = this.etudiantService.save(et);
	    return ResponseEntity.ok(etss);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteOne(@PathVariable Long id)
	{
		Etudiant et = this.etudiantService.getOne(id)
				.orElseThrow(
						()-> new ResourceNotFoundException("Etudiant non trouver")
						);
	    this.etudiantService.delete(et);
	    Map<String,Boolean> response = new HashMap();
	    response.put("delete",true);
	    return ResponseEntity.ok(response);
	    		
	}
}
