package com.udea.bancoudea.controller;

import com.udea.bancoudea.DTO.CustomerDTO;
import com.udea.bancoudea.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones de clientes bancarios
 */
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Clientes", description = "Gestión de clientes bancarios")
public class CustomerController {
    private final CustomerService customerFacade;

    public CustomerController(CustomerService customerFacade) {
        this.customerFacade = customerFacade;
    }

    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista de todos los clientes registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerFacade.getAllCustomer());
    }

    @Operation(summary = "Obtener cliente por ID", description = "Retorna la información de un cliente específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @Parameter(description = "ID único del cliente", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(customerFacade.getCustomerById(id));
    }

    @Operation(summary = "Crear nuevo cliente", description = "Registra un nuevo cliente en el sistema bancario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class)))
            @RequestBody CustomerDTO customerDTO) {
        if(customerDTO.getBalance() == null) {
            throw new IllegalArgumentException("El saldo no puede ser nulo");
        }
        return ResponseEntity.ok(customerFacade.createCustomer(customerDTO));
    }
}
