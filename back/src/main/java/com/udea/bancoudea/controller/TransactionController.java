package com.udea.bancoudea.controller;

import com.udea.bancoudea.DTO.TransactionDTO;
import com.udea.bancoudea.DTO.TransferRequestDTO;
import com.udea.bancoudea.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones de transacciones bancarias
 */
@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Transacciones", description = "Gestión de transferencias y consulta de transacciones")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Realizar transferencia", description = "Transfiere dinero entre dos cuentas bancarias de forma atómica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transferencia realizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o saldo insuficiente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transferMoney(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la transferencia a realizar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TransferRequestDTO.class)))
            @RequestBody TransferRequestDTO transferRequest) {
        TransactionDTO result = transactionService.transferMoney(transferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Obtener transacciones por cuenta", description = "Retorna el histórico de transacciones donde la cuenta aparece como remitente o destinatario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transacciones obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsForAccount(
            @Parameter(description = "Número de cuenta para consultar transacciones", required = true, example = "123456789")
            @PathVariable String accountNumber) {
        List<TransactionDTO> transactions = transactionService.getTransactionsForAccount(accountNumber);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Obtiene todas las transacciones (método placeholder para administración)
     */
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok().build();
    }

    /**
     * Obtiene una transacción por ID (método placeholder)
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
}
