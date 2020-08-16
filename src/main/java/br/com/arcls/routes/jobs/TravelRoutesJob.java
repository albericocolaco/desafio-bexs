package br.com.arcls.routes.jobs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TravelRoutesJob implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Teste 123");
    }
}
