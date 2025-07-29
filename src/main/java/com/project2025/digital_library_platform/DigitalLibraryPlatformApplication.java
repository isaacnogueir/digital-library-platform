package com.project2025.digital_library_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DigitalLibraryPlatformApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DigitalLibraryPlatformApplication.class, args);

        // Fecha o contexto corretamente quando a JVM for encerrada
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(">>> Encerrando contexto Spring...");
            context.close();
        }));
    }
}
