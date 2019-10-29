package com.cbdeveloper.app.accountant.domain;

import com.cbdeveloper.app.accountant.builder.DebtBuilder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Debt implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private BigDecimal originalAmount;
  private BigDecimal amountCorrected;
  private LocalDateTime dueDate;
  private LocalDateTime paymentDate;
  private Long overdueDay;

  public Debt(){}

  public Debt(DebtBuilder debtBuilder) {
    this.id = debtBuilder.id;
    this.name = debtBuilder.name;
    this.originalAmount = debtBuilder.originalAmount;
    this.amountCorrected = debtBuilder.amountCorrected;
    this.dueDate = debtBuilder.dueDate;
    this.paymentDate = debtBuilder.paymentDate;
    this.overdueDay = debtBuilder.overdueDay;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public BigDecimal getAmountCorrected() {
    return amountCorrected;
  }

  public void setAmountCorrected(BigDecimal amountCorrected) {
    this.amountCorrected = amountCorrected;
  }

  public LocalDateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDateTime dueDate) {
    this.dueDate = dueDate;
  }

  public Long getOverdueDay() {
    return overdueDay;
  }

  public void setOverdueDay(Long overdueDay) {
    this.overdueDay = overdueDay;
  }

  public LocalDateTime getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDateTime paymentDate) {
    this.paymentDate = paymentDate;
  }
}
