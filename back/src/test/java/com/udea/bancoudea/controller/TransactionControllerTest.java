package com.udea.bancoudea.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udea.bancoudea.DTO.TransferRequestDTO;
import com.udea.bancoudea.DTO.TransactionDTO;
import com.udea.bancoudea.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas para el controlador de transacciones
 */
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void transferMoney_Success() throws Exception {
        System.out.println("🧪 Probando: Endpoint POST /api/transactions/transfer - Transferencia exitosa");
        
        // Given
        TransferRequestDTO request = new TransferRequestDTO();
        request.setSenderAccountNumber("123456789");
        request.setReceiverAccountNumber("987654321");
        request.setAmount(200.0);

        TransactionDTO response = new TransactionDTO();
        response.setId(1L);
        response.setSenderAccountNumber("123456789");
        response.setReceiverAccountNumber("987654321");
        response.setAmount(200.0);
        response.setTimestamp(LocalDateTime.now());

        when(transactionService.transferMoney(any(TransferRequestDTO.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.senderAccountNumber").value("123456789"))
                .andExpect(jsonPath("$.receiverAccountNumber").value("987654321"))
                .andExpect(jsonPath("$.amount").value(200.0));
        
        System.out.println("✅ Endpoint funcionando correctamente - Status 201, datos correctos");
    }

    @Test
    void transferMoney_ValidationError() throws Exception {
        System.out.println("🧪 Probando: Endpoint POST /api/transactions/transfer - Error de validación");
        
        // Given
        TransferRequestDTO request = new TransferRequestDTO();
        request.setSenderAccountNumber("123456789");
        request.setReceiverAccountNumber("987654321");
        request.setAmount(0.0); // Monto inválido

        when(transactionService.transferMoney(any(TransferRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Amount must be greater than 0"));

        // When & Then
        mockMvc.perform(post("/api/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        
        System.out.println("✅ Error de validación manejado correctamente - Status 400");
    }

    @Test
    void getTransactionsForAccount_Success() throws Exception {
        System.out.println("🧪 Probando: Endpoint GET /api/transactions/account/{accountNumber} - Consulta exitosa");
        
        // Given
        String accountNumber = "123456789";
        List<TransactionDTO> transactions = Arrays.asList(
                createTransactionDTO(1L, "123456789", "987654321", 200.0),
                createTransactionDTO(2L, "555555555", "123456789", 100.0)
        );

        when(transactionService.getTransactionsForAccount(accountNumber)).thenReturn(transactions);

        // When & Then
        mockMvc.perform(get("/api/transactions/account/{accountNumber}", accountNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].senderAccountNumber").value("123456789"))
                .andExpect(jsonPath("$[1].receiverAccountNumber").value("123456789"));
        
        System.out.println("✅ Consulta de transacciones funcionando correctamente - Status 200, " + transactions.size() + " transacciones");
    }

    private TransactionDTO createTransactionDTO(Long id, String sender, String receiver, Double amount) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(id);
        dto.setSenderAccountNumber(sender);
        dto.setReceiverAccountNumber(receiver);
        dto.setAmount(amount);
        dto.setTimestamp(LocalDateTime.now());
        return dto;
    }
}
