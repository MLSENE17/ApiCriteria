package com.api.backendSchool.specification;


import com.api.backendSchool.exception.ResourceNotFoundException;
import com.api.backendSchool.model.Etudiant;
import com.api.backendSchool.model.Etudiant_;
import com.api.backendSchool.model.Prof;
import com.api.backendSchool.model.Prof_;
import com.api.backendSchool.repository.ProfRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;

@AllArgsConstructor
public class GenericSpecification<T> implements Specification<T> {
    @Autowired
    private SearchCriteria criteria;
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(criteria.getOperation().equalsIgnoreCase("<")){
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue());
        }else if(criteria.getOperation().equalsIgnoreCase(">")){
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue());
        }else if(criteria.getOperation().equalsIgnoreCase("=")){
            return criteriaBuilder.equal(
                    root.get(criteria.getKey()), criteria.getValue());
        }else if (criteria.getOperation().equalsIgnoreCase("like")){
            return criteriaBuilder.like(
                    root.get(criteria.getKey()), "%"+criteria.getValue()+"%");
        }else if (criteria.getOperation().equalsIgnoreCase("<>")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
            String[] dateall= criteria.getValue().split("<>");
            LocalDate localDate1 = LocalDate.parse(dateall[0]);
            LocalDate localDate2 = LocalDate.parse(dateall[1]);
            return criteriaBuilder.between(
                    root.get(criteria.getKey()), localDate1,localDate2);
        }else if(criteria.getOperation().equalsIgnoreCase("join")){
            String[] table = criteria.getKey().split("<>"); 
            return criteriaBuilder.equal(
                    root.get(table[0]).get(table[1]),criteria.getValue());//Integer.parseInt(criteria.getValue()));
        }
       return null;
    }
}
