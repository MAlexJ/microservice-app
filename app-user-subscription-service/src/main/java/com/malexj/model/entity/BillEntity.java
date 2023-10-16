package com.malexj.model.entity;

import lombok.Data;

@Data
public class BillEntity {

    private String number;

    /**
     * Ability to search for additional bills for a given bill number
     */
    private boolean advancedSearch;
}
