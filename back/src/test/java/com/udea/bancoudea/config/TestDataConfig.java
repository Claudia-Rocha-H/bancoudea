package com.udea.bancoudea.config;

import com.udea.bancoudea.entity.Customer;
import com.udea.bancoudea.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuración para cargar datos de prueba en el entorno de testing
 */
@Configuration
@Profile("test")
public class TestDataConfig {

    @Bean
    public CommandLineRunner loadTestData(CustomerRepository customerRepository) {
        return _ -> {
            // Limpiar datos existentes
            customerRepository.deleteAll();

            // Crear clientes de prueba
            Customer customer1 = new Customer();
            customer1.setAccountNumber("111111111");
            customer1.setFirstName("Ana");
            customer1.setLastName("Martínez");
            customer1.setBalance(2000.0);
            customerRepository.save(customer1);

            Customer customer2 = new Customer();
            customer2.setAccountNumber("222222222");
            customer2.setFirstName("Carlos");
            customer2.setLastName("López");
            customer2.setBalance(1500.0);
            customerRepository.save(customer2);

            Customer customer3 = new Customer();
            customer3.setAccountNumber("333333333");
            customer3.setFirstName("Laura");
            customer3.setLastName("Sánchez");
            customer3.setBalance(800.0);
            customerRepository.save(customer3);
        };
    }
}
