import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cbdeveloper.app.accountant.Application;
import com.cbdeveloper.app.accountant.domain.Debt;
import com.cbdeveloper.app.accountant.repository.DebtRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class DebtRestTest {

  private ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new JavaTimeModule());

  @MockBean
  private DebtRepository debtRepository;

  @Autowired
  private MockMvc mockMvc;

  @Before
  public void init() {

    Debt debtTest = new Debt();

    debtTest.setId(1L);
    debtTest.setName("Debt Carlos Barbosa");
    debtTest.setOriginalAmount(new BigDecimal("20.00"));
    debtTest.setDueDate(LocalDateTime.parse("2019-10-26T19:53:03.306"));
    debtTest.setPaymentDate(LocalDateTime.parse("2019-10-26T19:53:03.306"));
    debtTest.setOverdueDay(0L);
    debtTest.setAmountCorrected(new BigDecimal("20.00"));

    when(debtRepository.findById(1L)).thenReturn(Optional.of(debtTest));
    when(debtRepository.findAll()).thenReturn(Collections.singletonList(debtTest));
    when(debtRepository.save(any(Debt.class))).thenReturn(debtTest);

  }

  private Debt debtTest() throws Exception {
    Debt debtTest = new Debt();

    debtTest.setName("Debt Carlos Barbosa");
    debtTest.setOriginalAmount(new BigDecimal("20.00"));
    debtTest.setDueDate(LocalDateTime.parse("2019-10-26T19:53:03.306"));
    debtTest.setPaymentDate(LocalDateTime.parse("2019-10-26T19:53:03.306"));

    return debtTest;
  }

  @Test
  public void shouldCreateDebt() throws Exception {

   mockMvc
        .perform(
        post("/debt")
          .contentType(APPLICATION_JSON_UTF8_VALUE)
          .content(objectMapper.writeValueAsString(debtTest())))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", equalTo(debtTest().getName())))
        .andExpect(jsonPath("$.originalAmount", equalTo(20.00)))
        .andExpect(jsonPath("$.dueDate", equalTo("2019-10-26T19:53:03.306")))
        .andExpect(jsonPath("$.paymentDate", equalTo("2019-10-26T19:53:03.306")))
        .andExpect(jsonPath("$.overdueDay", equalTo(0)))
        .andExpect(jsonPath("$.amountCorrected", equalTo(20.00)));
  }

  @Test
  public void shouldDeleteDebtById() throws Exception {

    List<Debt> debts = this.debtRepository.findAll();

    mockMvc
        .perform(
            delete("/debt/{id}", debts.get(0).getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  public void shouldUpdateDebtById() throws Exception {

    List<Debt> debt = this.debtRepository.findAll();

    mockMvc
        .perform(
            put("/debt/{id}", debt.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(debtTest())))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(debtTest().getName())))
        .andExpect(jsonPath("$.originalAmount", equalTo(20.0)))
        .andExpect(jsonPath("$.dueDate", equalTo("2019-10-26T19:53:03.306")))
        .andExpect(jsonPath("$.paymentDate", equalTo("2019-10-26T19:53:03.306")))
        .andExpect(jsonPath("$.overdueDay", equalTo(0)))
        .andExpect(jsonPath("$.amountCorrected", equalTo(20.0)));
  }

  @Test
  public void shouldGetAllDebts() throws Exception {

    mockMvc
        .perform(
            get("/debt"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect((jsonPath("$.[*]")).isNotEmpty());
  }


}
