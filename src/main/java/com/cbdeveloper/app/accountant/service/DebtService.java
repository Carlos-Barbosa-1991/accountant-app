package com.cbdeveloper.app.accountant.service;

import com.cbdeveloper.app.accountant.converter.DebtConverter;
import com.cbdeveloper.app.accountant.domain.Debt;
import com.cbdeveloper.app.accountant.model.DebtRequest;
import com.cbdeveloper.app.accountant.repository.DebtRepository;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DebtService {

  private final DebtRepository debtRepository;
  private final DebtConverter debtConverter;

  @Value("${twoPercentFine.startDay}")
  private Long twoPercentFineStartDay;

  @Value("${twoPercentFine.finishDay}")
  private Long twoPercentFineFinishDay;

  @Value("${twoPercentFine.finePercentage}")
  private BigDecimal twoPercentFine;

  @Value("${twoPercentFine.interestPercentagePerDay}")
  private BigDecimal twoPercentFineInterestPerDay;

  @Value("${threePercentFine.startDay}")
  private Long threePercentFineStartDay;

  @Value("${threePercentFine.finishDay}")
  private Long threePercentFineFinishDay;

  @Value("${threePercentFine.finePercentage}")
  private BigDecimal threePercentFine;

  @Value("${threePercentFine.interestPercentagePerDay}")
  private BigDecimal threePercentFineInterestPerDay;

  @Value("${fivePercentFine.startDay}")
  private Long fivePercentFineStartDay;

  @Value("${fivePercentFine.finePercentage}")
  private BigDecimal fivePercentFine;

  @Value("${fivePercentFine.interestPercentagePerDay}")
  private BigDecimal fivePercentFineInterestPerDay;

  public DebtService(
      final DebtRepository debtRepository,
      final DebtConverter debtConverter){
    this.debtRepository = debtRepository;
    this.debtConverter = debtConverter;
  }

  public Debt createDebt(DebtRequest debtRequest){
    Debt currentDebt = this.debtConverter.toEntity(debtRequest);
    Debt debtCorrected = this.calculateDebtFine(currentDebt);

    return this.debtRepository.save(debtCorrected);
  }

  public List<Debt> findAll(){
    return this.debtRepository.findAll();
  }

  public Debt updateDebt(Long id, DebtRequest debtRequest){
    Debt currentDebt = this.debtRepository.findById(id)
        .orElseThrow(EntityNotFoundException::new);

    currentDebt.setName(debtRequest.getName());
    currentDebt.setOriginalAmount(debtRequest.getOriginalAmount());
    currentDebt.setDueDate(debtRequest.getDueDate());
    currentDebt.setPaymentDate(debtRequest.getPaymentDate());

    Debt debtCorrected = this.calculateDebtFine(currentDebt);

    return this.debtRepository.save(debtCorrected);
  }

  public void deleteDebt(Long id){
    Debt currentDebt = this.debtRepository.findById(id)
        .orElseThrow(EntityNotFoundException::new);

    this.debtRepository.delete(currentDebt);
  }

  private Debt calculateDebtFine(Debt currentDebt){

    Long paymentDateInMilli = this.debtConverter.paymentDateInMilli(currentDebt.getPaymentDate());
    Long dueDateInMilli = this.debtConverter.dueDateInMilli(currentDebt.getDueDate());

    BigDecimal amountCorrected = currentDebt.getOriginalAmount();

    long overdueDay = (paymentDateInMilli - dueDateInMilli) / 86400000;

    currentDebt.setOverdueDay(overdueDay < 0 ? 0 : overdueDay);

    if(currentDebt.getOverdueDay() >= twoPercentFineStartDay
        && currentDebt.getOverdueDay() <= twoPercentFineFinishDay){

      BigDecimal fineAmount = currentDebt.getOriginalAmount()
          .multiply(twoPercentFine);

      BigDecimal interestPerDay = currentDebt.getOriginalAmount()
          .multiply(twoPercentFineInterestPerDay.multiply(new BigDecimal(currentDebt.getOverdueDay())));

      amountCorrected = currentDebt.getOriginalAmount().add(fineAmount).add(interestPerDay);

    } else if(currentDebt.getOverdueDay() > threePercentFineStartDay
        && currentDebt.getOverdueDay() <= threePercentFineFinishDay){

      BigDecimal fineAmount = currentDebt.getOriginalAmount()
          .multiply(threePercentFine);

      BigDecimal interestPerDay = currentDebt.getOriginalAmount()
          .multiply(threePercentFineInterestPerDay.multiply(new BigDecimal(currentDebt.getOverdueDay())));

      amountCorrected = currentDebt.getOriginalAmount().add(fineAmount).add(interestPerDay);

    } else if(currentDebt.getOverdueDay() > fivePercentFineStartDay){

      BigDecimal fineAmount = currentDebt.getOriginalAmount()
          .multiply(fivePercentFine);

      BigDecimal interestPerDay = currentDebt.getOriginalAmount()
          .multiply(fivePercentFineInterestPerDay.multiply(new BigDecimal(currentDebt.getOverdueDay())));

      amountCorrected = currentDebt.getOriginalAmount().add(fineAmount).add(interestPerDay);

    }

    currentDebt.setAmountCorrected(amountCorrected);

    return currentDebt;
  }

}
