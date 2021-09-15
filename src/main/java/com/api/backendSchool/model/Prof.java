package com.api.backendSchool.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Table(name="profs")
@Entity
@Getter()
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @JsonBackReference
    @OneToMany(mappedBy="prof",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Etudiant> etudiant  ;
}
