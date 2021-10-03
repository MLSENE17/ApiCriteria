package com.api.backendSchool.specification;



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
        if(criteria.getOperation().equalsIgnoreCase("equal")){
            return criteriaBuilder.equal(
                    root.get(criteria.getKey()), criteria.getValue());}
        else if(criteria.getOperation().equalsIgnoreCase("<")){
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }else if(criteria.getOperation().equalsIgnoreCase(">")){
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }else if(criteria.getOperation().equalsIgnoreCase("=")){
            return criteriaBuilder.equal(
                    root.get(criteria.getKey()), criteria.getValue());
        }else if (criteria.getOperation().equalsIgnoreCase("like")){
            return criteriaBuilder.like(
                    root.get(criteria.getKey()), "%"+criteria.getValue()+"%");
        }else if (criteria.getOperation().equalsIgnoreCase("<>")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
            String[] dateall= criteria.getValue().toString().split("<>");
            LocalDate localDate1 = LocalDate.parse(dateall[0]);
            LocalDate localDate2 = LocalDate.parse(dateall[1]);
            return criteriaBuilder.between(
                    root.get(criteria.getKey()), localDate1,localDate2);
        }else if(criteria.getOperation().equalsIgnoreCase("join")){
            String[] table = criteria.getKey().split("<>");
            return criteriaBuilder.equal(
                    root.get(table[0]).get(table[1]),criteria.getValue());//Integer.parseInt(criteria.getValue()));
        }else if(criteria.getOperation().equalsIgnoreCase("!")){
            return criteriaBuilder.notEqual(
                    root.get(criteria.getKey()), criteria.getValue());
        }
       return null;
    }
}
