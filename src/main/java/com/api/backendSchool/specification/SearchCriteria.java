package com.api.backendSchool.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

}
