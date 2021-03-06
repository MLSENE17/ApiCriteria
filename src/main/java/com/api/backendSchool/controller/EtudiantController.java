package com.api.backendSchool.controller;

import com.api.backendSchool.model.Type;
import com.api.backendSchool.specification.GeneriqueSpecificationRequest;
import com.api.backendSchool.specification.PageNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.api.backendSchool.projection.EtudiantProjection;
import com.api.backendSchool.repository.EtudiantRepository;
import com.api.backendSchool.specification.EtudiantSpecificationsBuilder;
import com.api.backendSchool.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.api.backendSchool.exception.ResourceNotFoundException;
import com.api.backendSchool.model.Etudiant;
import com.api.backendSchool.model.SearchEtudiant;
import com.api.backendSchool.service.EtudiantService;

import javax.validation.Valid;


@RestController
@RequestMapping("etudiant")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EtudiantController {
	@Autowired
	private EtudiantService etudiantService;
	@Autowired
	private EtudiantRepository etudiantRepository;
	@GetMapping("/projection")
	public List<Etudiant> getProjection()
	{
		return etudiantRepository.getChampSelected();
	}
	@PostMapping("/fetch")
	public ResponseEntity<?> getAgeAndPrenom(@Valid  @RequestBody GeneriqueSpecificationRequest search){
		//List<SearchCriteria> searchCriteria = search.get("items");
		//PageNumber pageNumber = null;
		//pageNumber =search.get("pageable");
		EtudiantSpecificationsBuilder<Etudiant> builder = new EtudiantSpecificationsBuilder();
		Object tmp=null;
		for(SearchCriteria sc:search.getItems()){
			if(sc.getKey().equals("type")){
				if (sc.getValue().equals("ACTIF")) {
					tmp = Type.ACTIF;
				} else if (sc.getValue().equals("DISABLE")) {
					tmp = Type.DISABLE;
				}
			}
			else{
				tmp=sc.getValue();
			}
			builder.with(sc.getKey(),sc.getOperation(),tmp);
		}
		Specification<Etudiant> spec = builder.build();
		Pageable paging = PageRequest.of(search.getPageable().getPage(),search.getPageable().getSize());
		Page<Etudiant> pageTuts = etudiantRepository.findAll(spec,paging);
		List<Etudiant> etu;
		etu = pageTuts.getContent();
		Map<String, Object> response = new HashMap<>();
		response.put("tutorials", etu);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);
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
