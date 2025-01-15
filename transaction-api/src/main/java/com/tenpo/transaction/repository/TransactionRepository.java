package com.tenpo.transaction.repository;

import com.tenpo.transaction.entity.TransactionEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    @Query(value = "SELECT COUNT(*) FROM tenpo.transactions WHERE user_name = :userName", nativeQuery = true)
    Long checkMaxTransactionsPerUser(@Param("userName") String userName);

    @Query(value = "SELECT EXISTS ( " +
            "SELECT 1 " +
            "FROM tenpo.transactions " +
            "WHERE user_name = :userName " +
            "  AND commerce = :commerce " +
            "  AND total_amount = :totalAmount " +
            "  AND transaction_date = :transactionDate " +
            ") AS is_duplicate",
            nativeQuery = true)
    boolean validateDuplicateTransaction(@Param("userName") String userName,
                                         @Param("commerce") String commerce,
                                         @Param("totalAmount") int totalAmount,
                                         @Param("transactionDate") Instant transactionDate);


    @Query(value = "SELECT * FROM tenpo.transactions WHERE user_name = :userName", nativeQuery = true)
    Optional<TransactionEntity[]> findByUserName(@Param("userName") String userName);
}
