package com.api.backendSchool.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.backendSchool.exception.ResourceNotFoundException;
import com.api.backendSchool.model.Classe;
import com.api.backendSchool.service.ClasseService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("classe")
public class ClasseController {
	@Autowired
	private ClasseService classeService;
	@GetMapping("/all")
	public List<Classe> getAll(){
		return  this.classeService.getAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Classe> getOne(@PathVariable Long id){
		Classe cl = this.classeService.getOne(id)
				.orElseThrow(
						()->new  ResourceNotFoundException("Classe non trouve")
						);
		return ResponseEntity.ok(cl);
				
		
	}
	@PostMapping("/create")
	public Classe createOne(@RequestBody Classe cl) {
		return this.classeService.saveClasse(cl);
	}
	@PutMapping("/edit/{id}")
	public ResponseEntity<Classe> updateOne(@PathVariable Long id,@RequestBody Classe cl)
	{
		Classe cls = this.classeService.getOne(id)
				.orElseThrow(
						()->new  ResourceNotFoundException("Classe non trouve")
						);
	    cls.setLibelle(cl.getLibelle());
	    Classe clupdate = this.classeService.saveClasse(cls);
	    return ResponseEntity.ok(clupdate);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteOne(@PathVariable Long id){
		Classe cl = this.classeService.getOne(id)
				.orElseThrow(
						()->new  ResourceNotFoundException("Classe non trouve")
						);
	   this.classeService.delete(cl);
	   Map<String,Boolean> response = new HashMap();
	   response.put("delete",true);
	   return ResponseEntity.ok(response);	
		
	}
}
