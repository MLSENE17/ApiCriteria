package com.api.backendSchool.specification;

import com.api.backendSchool.model.Etudiant;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EtudiantSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public EtudiantSpecificationsBuilder() {
        this.params = new ArrayList<SearchCriteria>();
    }

    public EtudiantSpecificationsBuilder with(String key,String operation,String values) {
        this.params.add(new SearchCriteria(key,operation,values));
        return this;
    }
    public Specification<Etudiant> build(){
        if(this.params.size()==0){
            return null;
        }
        List<Specification> specs = params.stream()
                .map(GenericSpecification<Etudiant>::new)
                .collect(Collectors.toList());
        Specification result = specs.get(0);
        for(int i=1;i<params.size();i++){
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return  result;
    }
}
