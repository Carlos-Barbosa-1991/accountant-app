package com.cbdeveloper.app.accountant.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DebtRequest {

  private String name;

  private BigDecimal originalAmount;

  private LocalDateTime dueDate;

  private LocalDateTime paymentDate;

  public DebtRequest(){}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getOriginalAmount() {
    return originalAmount;
  }

  public void setOriginalAmount(BigDecimal originalAmount) {
    this.originalAmount = originalAmount;
  }

  public LocalDateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDateTime dueDate) {
    this.dueDate = dueDate;
  }

  public LocalDateTime getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDateTime paymentDate) {
    this.paymentDate = paymentDate;
  }
}
