package com.api.backendSchool.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.backendSchool.model.Etudiant;
import com.api.backendSchool.repository.ClasseRepository;
import com.api.backendSchool.repository.EtudiantRepository;

import javax.transaction.Transactional;

@Service
public class EtudiantService {
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Autowired
	private ClasseRepository classeRepository;
	public List<Etudiant> searchEtudiant(String keyword){
		return this.etudiantRepository.findEtudiantsByKeyword(keyword);
	}
	@Transactional
	public List<Etudiant> getAll()
	{
		return this.etudiantRepository.findAll(Sort.by("id").ascending());
	}
	public Optional<Etudiant> getOne(Long id){
		return this.etudiantRepository.findById(id);
	}
	public void delete(Etudiant et)
	{
		 this.etudiantRepository.delete(et);
	}
	public Etudiant save(Etudiant ets)
	{
		Etudiant et1 = this.etudiantRepository.save(ets);
		return et1;
	}
	public Map<String,Long> total() {
		 Map<String,Long> response = new HashMap<String, Long>();
		 response.put("etudiant", this.etudiantRepository.count());
		 response.put("classe",this.classeRepository.count());
		 return response;
	}
}
