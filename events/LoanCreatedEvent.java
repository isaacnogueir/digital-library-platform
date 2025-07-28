package com.project2025.auth_2.events;

import org.springframework.context.ApplicationEvent;

public class LoanCreatedEvent extends ApplicationEvent {

    public final Long loanId;

    public LoanCreatedEvent(Long loanId) {
        super(loanId);
        this.loanId = loanId;
    }
}