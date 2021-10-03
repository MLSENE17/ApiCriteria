package com.api.backendSchool.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneriqueSpecificationRequest {
    private List<SearchCriteria> items;
    private PageNumber pageable ;
}
