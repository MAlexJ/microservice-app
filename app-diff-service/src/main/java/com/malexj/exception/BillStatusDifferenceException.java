package com.malexj.exception;

import com.malexj.model.BillStatus;

import java.util.Set;

public class BillStatusDifferenceException extends RuntimeException {

    public BillStatusDifferenceException(Set<BillStatus> diff) {
        super("Status difference detected: " + diff.toString());
    }
}
