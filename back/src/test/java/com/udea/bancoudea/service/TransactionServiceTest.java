package com.udea.bancoudea.service;

import com.udea.bancoudea.DTO.TransferRequestDTO;
import com.udea.bancoudea.DTO.TransactionDTO;
import com.udea.bancoudea.entity.Customer;
import com.udea.bancoudea.repository.CustomerRepository;
import com.udea.bancoudea.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el servicio de transacciones
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Customer sender;
    private Customer receiver;
    private TransferRequestDTO transferRequest;

    @BeforeEach
    void setUp() {
        sender = new Customer();
        sender.setId(1L);
        sender.setAccountNumber("123456789");
        sender.setFirstName("Juan");
        sender.setLastName("Pérez");
        sender.setBalance(1000.0);

        receiver = new Customer();
        receiver.setId(2L);
        receiver.setAccountNumber("987654321");
        receiver.setFirstName("María");
        receiver.setLastName("García");
        receiver.setBalance(500.0);

        transferRequest = new TransferRequestDTO();
        transferRequest.setSenderAccountNumber("123456789");
        transferRequest.setReceiverAccountNumber("987654321");
        transferRequest.setAmount(200.0);
    }

    @Test
    void transferMoney_Success() {
        System.out.println("🧪 Probando: Transferencia exitosa con fondos suficientes");
        
        // Given
        when(customerRepository.findByAccountNumberForUpdate("123456789"))
                .thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumberForUpdate("987654321"))
                .thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        TransactionDTO result = transactionService.transferMoney(transferRequest);

        // Then
        assertNotNull(result);
        assertEquals("123456789", result.getSenderAccountNumber());
        assertEquals("987654321", result.getReceiverAccountNumber());
        assertEquals(200.0, result.getAmount());

        verify(customerRepository, times(2)).save(any(Customer.class));
        verify(transactionRepository, times(1)).save(any());
        
        System.out.println("✅ Transferencia exitosa - Monto: $" + result.getAmount());
    }

    @Test
    void transferMoney_InsufficientFunds() {
        System.out.println("🧪 Probando: Transferencia con fondos insuficientes");
        
        // Given
        transferRequest.setAmount(1500.0); // Más que el saldo disponible
        when(customerRepository.findByAccountNumberForUpdate("123456789"))
                .thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumberForUpdate("987654321"))
                .thenReturn(Optional.of(receiver));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transferMoney(transferRequest);
        });

        assertEquals("Insufficient funds. Available: 1000.0, Required: 1500.0", exception.getMessage());
        
        System.out.println("✅ Error esperado capturado: " + exception.getMessage());
    }

    @Test
    void transferMoney_SameAccount() {
        System.out.println("🧪 Probando: Transferencia a la misma cuenta (debe fallar)");
        
        // Given
        transferRequest.setReceiverAccountNumber("123456789"); // Misma cuenta
        when(customerRepository.findByAccountNumberForUpdate("123456789"))
                .thenReturn(Optional.of(sender));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transferMoney(transferRequest);
        });

        assertEquals("Sender and receiver cannot be the same account", exception.getMessage());
        
        System.out.println("✅ Error esperado capturado: " + exception.getMessage());
    }

    @Test
    void transferMoney_SenderNotFound() {
        System.out.println("🧪 Probando: Transferencia con cuenta remitente inexistente");
        
        // Given
        when(customerRepository.findByAccountNumberForUpdate("123456789"))
                .thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transferMoney(transferRequest);
        });

        assertEquals("Sender account not found: 123456789", exception.getMessage());
        
        System.out.println("✅ Error esperado capturado: " + exception.getMessage());
    }

    @Test
    void transferMoney_InvalidAmount() {
        System.out.println("🧪 Probando: Transferencia con monto inválido (cero)");
        
        // Given
        transferRequest.setAmount(0.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transferMoney(transferRequest);
        });

        assertEquals("Amount must be greater than 0", exception.getMessage());
        
        System.out.println("✅ Error esperado capturado: " + exception.getMessage());
    }
}
