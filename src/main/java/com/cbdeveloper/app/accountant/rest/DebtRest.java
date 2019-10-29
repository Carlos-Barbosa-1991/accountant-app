package com.cbdeveloper.app.accountant.rest;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.cbdeveloper.app.accountant.domain.Debt;
import com.cbdeveloper.app.accountant.model.DebtRequest;
import com.cbdeveloper.app.accountant.service.DebtService;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debt")
public class DebtRest {

  private DebtService debtService;

  public DebtRest(DebtService debtService) {
    this.debtService = debtService;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public Debt createDebt(@RequestBody @Valid DebtRequest debtRequest) {
    return this.debtService.createDebt(debtRequest);
  }

  @GetMapping
  @ResponseStatus(OK)
  public List<Debt> findAll() {
    return this.debtService.findAll();
  }

  @PutMapping("/{id}")
  @ResponseStatus(OK)
  public Debt updateDebt(@PathVariable Long id,
      @RequestBody @Valid DebtRequest debtRequest) {
    return this.debtService.updateDebt(id, debtRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  public void deleteDebt(@PathVariable Long id) {
    this.debtService.deleteDebt(id);
  }

}
