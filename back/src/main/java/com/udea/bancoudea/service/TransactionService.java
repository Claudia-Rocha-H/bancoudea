package com.udea.bancoudea.service;

import com.udea.bancoudea.DTO.TransactionDTO;
import com.udea.bancoudea.DTO.TransferRequestDTO;
import com.udea.bancoudea.entity.Customer;
import com.udea.bancoudea.entity.Transaction;
import com.udea.bancoudea.repository.CustomerRepository;
import com.udea.bancoudea.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionDTO transferMoney(TransferRequestDTO transferRequest) {
        logger.info("Starting transfer from {} to {} for amount {}", 
                   transferRequest.getSenderAccountNumber(), 
                   transferRequest.getReceiverAccountNumber(), 
                   transferRequest.getAmount());

        // Validate input
        validateTransferRequest(transferRequest);

        // Use pessimistic locking to prevent race conditions
        Customer sender = customerRepository.findByAccountNumberForUpdate(transferRequest.getSenderAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found: " + transferRequest.getSenderAccountNumber()));

        Customer receiver = customerRepository.findByAccountNumberForUpdate(transferRequest.getReceiverAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found: " + transferRequest.getReceiverAccountNumber()));

        // Validate business rules
        if (sender.getAccountNumber().equals(receiver.getAccountNumber())) {
            throw new IllegalArgumentException("Sender and receiver cannot be the same account");
        }

        if (sender.getBalance() < transferRequest.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds. Available: " + sender.getBalance() + ", Required: " + transferRequest.getAmount());
        }

        // Perform the transfer
        sender.setBalance(sender.getBalance() - transferRequest.getAmount());
        receiver.setBalance(receiver.getBalance() + transferRequest.getAmount());

        // Save customers
        customerRepository.save(sender);
        customerRepository.save(receiver);

        // Create and save transaction
        Transaction transaction = new Transaction();
        transaction.setSenderAccountNumber(sender.getAccountNumber());
        transaction.setReceiverAccountNumber(receiver.getAccountNumber());
        transaction.setAmount(transferRequest.getAmount());
        transaction.setTimestamp(LocalDateTime.now());
        
        transaction = transactionRepository.save(transaction);

        logger.info("Transfer completed successfully. Transaction ID: {}", transaction.getId());

        // Return transaction DTO
        return mapToTransactionDTO(transaction);
    }

    public List<TransactionDTO> getTransactionsForAccount(String accountNumber) {
        logger.info("Retrieving transactions for account: {}", accountNumber);
        
        List<Transaction> transactions = transactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(accountNumber, accountNumber);
        
        return transactions.stream()
                .map(this::mapToTransactionDTO)
                .collect(Collectors.toList());
    }

    private void validateTransferRequest(TransferRequestDTO request) {
        if (request.getSenderAccountNumber() == null || request.getSenderAccountNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Sender account number cannot be null or empty");
        }
        if (request.getReceiverAccountNumber() == null || request.getReceiverAccountNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Receiver account number cannot be null or empty");
        }
        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    private TransactionDTO mapToTransactionDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setSenderAccountNumber(transaction.getSenderAccountNumber());
        dto.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
        dto.setAmount(transaction.getAmount());
        dto.setTimestamp(transaction.getTimestamp());
        return dto;
    }
}
