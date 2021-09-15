package com.api.backendSchool.model;
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Table(name="etudiants")
@Entity
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class Etudiant implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false)
	private String prenom;
	@Column(nullable=false)
	private String nom;
	@Column(unique=true,nullable=false)
	private String email;
	@Column(name="signin_date",nullable = false)
	private LocalDate signinDate;
	@ManyToOne
	@JoinColumn(name="classe_id",nullable=false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Classe classe;
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="prof_id",nullable=false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Prof prof;
	private int age;
}
