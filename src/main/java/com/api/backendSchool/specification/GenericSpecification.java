package com.api.backendSchool.specification;


import com.api.backendSchool.model.Etudiant;
import com.api.backendSchool.model.Etudiant_;
import com.api.backendSchool.model.Prof;
import com.api.backendSchool.model.Prof_;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
           final  Join<Prof,Etudiant> prof = root.join(Prof_.ETUDIANT,JoinType.LEFT);
            return criteriaBuilder.equal(prof.get(Etudiant_.PROF),criteria.getValue());
           // return  etudiant.get(Etudiant_.prof);

        }
       return null;
    }
}
