package com.api.backendSchool.model;
import java.io.Serializable;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter()
@Setter
@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class SearchEtudiant implements Serializable{
	@NotNull
	private String keyword;
}
