package com.project2025.digital_library_platform.events;

import com.project2025.digital_library_platform.DTOs.DomainEventDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    @EventListener
    public void handlerBookCreatedEvent(BookCreatedEvent event) {
        DomainEventDTO message = new DomainEventDTO(
                "BOOK_CREATED",
                "BOOK",
                event.getBookId(),
                "Novo livro criado com ID:" + event.getBookId()
        );

        // Envia para o RabbitMQ
        rabbitTemplate.convertAndSend("library.events", "audit.log", message);

        System.out.println("Evento publicado com RabbitMQ: " + message);
    }

    @EventListener
    public void handlerUserCreatedEvent(UserRegisteredEvent event){
        DomainEventDTO message = new DomainEventDTO(
                "USER_REGISTER",
                "USER",
                event.getUserId(),
                "Novo usuário criado com ID:" + event.getUserId()
        );

        rabbitTemplate.convertAndSend("library.events","audit.log", message);
        System.out.println("Evento publicado com RabbitMQ: " + message);
    }

    public void handlerLoanCreatedEvent(LoanCreatedEvent event){
        DomainEventDTO message = new DomainEventDTO(
                "LOAN_REGISTER",
                "LOAN",
                event.getLoanId(),
                "Novo empréstimo efetuado com o ID:" + event.getLoanId()
        );
        rabbitTemplate.convertAndSend("library.events","audit.log",message);
        System.out.println("Evento publicado com RabbitMQ: " + message);
    }


}