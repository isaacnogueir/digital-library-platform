package com.project2025.digital_library_platform.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LoanCreatedEvent extends ApplicationEvent {

    public final Long loanId;

    public LoanCreatedEvent(Long loanId) {
        super(loanId);
        this.loanId = loanId;
    }
}