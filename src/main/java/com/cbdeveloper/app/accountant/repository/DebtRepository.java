package com.cbdeveloper.app.accountant.repository;

import com.cbdeveloper.app.accountant.domain.Debt;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepository extends JpaRepository<Debt, String> {

  Optional<Debt> findById(Long id);
}
