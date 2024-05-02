package dev.codescreen.Repository;

import dev.codescreen.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
