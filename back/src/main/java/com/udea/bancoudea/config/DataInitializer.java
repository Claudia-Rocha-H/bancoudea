package com.udea.bancoudea.config;

import com.udea.bancoudea.entity.Customer;
import com.udea.bancoudea.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializador de datos para poblar la base de datos al arrancar la aplicación
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya hay datos
        if (customerRepository.count() == 0) {
            System.out.println("🔄 Inicializando datos de prueba...");
            
            // Crear clientes de prueba
            Customer customer1 = new Customer();
            customer1.setAccountNumber("123456789");
            customer1.setFirstName("Juan");
            customer1.setLastName("Pérez");
            customer1.setBalance(1000.0);
            customerRepository.save(customer1);

            Customer customer2 = new Customer();
            customer2.setAccountNumber("987654321");
            customer2.setFirstName("María");
            customer2.setLastName("García");
            customer2.setBalance(500.0);
            customerRepository.save(customer2);

            Customer customer3 = new Customer();
            customer3.setAccountNumber("555555555");
            customer3.setFirstName("Carlos");
            customer3.setLastName("López");
            customer3.setBalance(2500.0);
            customerRepository.save(customer3);

            Customer customer4 = new Customer();
            customer4.setAccountNumber("111111111");
            customer4.setFirstName("Ana");
            customer4.setLastName("Martínez");
            customer4.setBalance(850.0);
            customerRepository.save(customer4);

            Customer customer5 = new Customer();
            customer5.setAccountNumber("999999999");
            customer5.setFirstName("Luis");
            customer5.setLastName("Rodríguez");
            customer5.setBalance(3000.0);
            customerRepository.save(customer5);

            Customer customer6 = new Customer();
            customer6.setAccountNumber("888888888");
            customer6.setFirstName("Sofia");
            customer6.setLastName("Hernández");
            customer6.setBalance(1200.0);
            customerRepository.save(customer6);

            Customer customer7 = new Customer();
            customer7.setAccountNumber("777777777");
            customer7.setFirstName("Diego");
            customer7.setLastName("González");
            customer7.setBalance(800.0);
            customerRepository.save(customer7);

            Customer customer8 = new Customer();
            customer8.setAccountNumber("666666666");
            customer8.setFirstName("Laura");
            customer8.setLastName("Sánchez");
            customer8.setBalance(1500.0);
            customerRepository.save(customer8);

            System.out.println("✅ Datos de prueba inicializados correctamente - " + customerRepository.count() + " clientes creados");
        } else {
            System.out.println("ℹ️ Base de datos ya contiene " + customerRepository.count() + " clientes");
        }
    }
}
