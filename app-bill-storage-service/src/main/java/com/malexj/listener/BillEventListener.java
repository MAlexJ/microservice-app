package com.malexj.listener;

import com.malexj.entity.Bill;
import com.malexj.entity.BillStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Slf4j
@RepositoryEventHandler
public class BillEventListener {

    @HandleBeforeCreate
    public void handleBeforeCreate(Bill bill) {
        log.info("Save new bill: {}", bill);
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(BillStatus status) {
        log.info("Save new status: {}", status);
    }

    @HandleAfterCreate
    public void handleAfterCreate(Bill bill) {
        log.info("Bill was successfully saved: {}", bill);
    }

    @HandleAfterCreate
    public void handleAfterCreate(BillStatus status) {
        log.info("Status was successfully saved: {}", status);
    }

}