package com.api.backendSchool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backendSchool.model.Classe;
import com.api.backendSchool.repository.ClasseRepository;

@Service
public class ClasseService {
	@Autowired
	private ClasseRepository claseRepository;
	public List<Classe> getAll(){
		return this.claseRepository.findAll();
	}
	public Optional<Classe> getOne(Long id){
		return this.claseRepository.findById(id);
	}
	public Classe saveClasse(Classe classe) {
		Classe cl = this.claseRepository.save(classe);
		return cl;
		
	}
	public void delete(Classe classe) {
		this.claseRepository.delete(classe);
	}
}
