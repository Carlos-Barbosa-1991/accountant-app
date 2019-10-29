package com.cbdeveloper.app.accountant.builder;

import com.cbdeveloper.app.accountant.domain.Debt;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DebtBuilder {

  public Long id;
  public String name;
  public BigDecimal originalAmount;
  public BigDecimal amountCorrected;
  public LocalDateTime dueDate;
  public LocalDateTime paymentDate;
  public Long overdueDay;

  public DebtBuilder(){}

  public DebtBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public DebtBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public DebtBuilder withOriginalAmount(BigDecimal originalAmount) {
    this.originalAmount = originalAmount;
    return this;
  }

  public DebtBuilder withAmountCorrected(BigDecimal amountCorrected) {
    this.amountCorrected = amountCorrected;
    return this;
  }

  public DebtBuilder withDueDate(LocalDateTime dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  public DebtBuilder withPaymentDate(LocalDateTime paymentDate) {
    this.paymentDate = paymentDate;
    return this;
  }

  public DebtBuilder withOverdueDay(Long overdueDay) {
    this.overdueDay = overdueDay;
    return this;
  }

  public Debt build() {
    return new Debt(this);
  }

}
