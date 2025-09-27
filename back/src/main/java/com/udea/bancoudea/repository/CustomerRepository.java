package com.udea.bancoudea.repository;

import com.udea.bancoudea.entity.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAccountNumber(String accountNumber);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Customer c where c.accountNumber = :accountNumber")
    Optional<Customer> findByAccountNumberForUpdate(@Param("accountNumber") String accountNumber);
}
