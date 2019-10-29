package com.cbdeveloper.app.accountant.converter;

import com.cbdeveloper.app.accountant.builder.DebtBuilder;
import com.cbdeveloper.app.accountant.domain.Debt;
import com.cbdeveloper.app.accountant.model.DebtRequest;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class DebtConverter {

  public Debt toEntity(DebtRequest debtRequest) {
    return new DebtBuilder()
        .withName(debtRequest.getName())
        .withOriginalAmount(debtRequest.getOriginalAmount())
        .withDueDate(debtRequest.getDueDate())
        .withPaymentDate(debtRequest.getPaymentDate())
        .build();

  }

  public Long paymentDateInMilli(LocalDateTime paymentDate) {
    return paymentDate
        .toInstant(OffsetDateTime.now().getOffset())
        .toEpochMilli();

  }

  public Long dueDateInMilli(LocalDateTime dueDate) {
    return dueDate
        .toInstant(OffsetDateTime.now().getOffset())
        .toEpochMilli();

  }

}
