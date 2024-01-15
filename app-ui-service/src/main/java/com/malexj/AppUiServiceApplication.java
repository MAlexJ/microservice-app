package com.malexj;

import com.malexj.model.Bill;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppUiServiceApplication {

  /**
   * { "url": "https://itd.rada.gov.ua/billInfo/Bills/Card/42664", "number": "9672", "name": "text
   * description", "registrationDate": "2023-09-04" }
   */
  @Bean
  public Collection<Bill> billList() {
    Collection<Bill> collection = Collections.synchronizedCollection(new ArrayList<>());
    Bill b = new Bill();
    b.setLink("https://itd.rada.gov.ua/billInfo/Bills/Card/42664");
    b.setRegistrationDate(LocalDate.now());
    b.setNumber("9672");
    b.setName("text description");
    collection.add(b);
    Bill b1 = new Bill();
    b1.setLink("https://itd.rada.gov.ua/billInfo/Bills/Card/42665");
    b1.setRegistrationDate(LocalDate.now().minusDays(1));
    b1.setNumber("9673");
    b1.setName("New description");
    collection.add(b1);
    return collection;
  }

  public static void main(String[] args) {
    SpringApplication.run(AppUiServiceApplication.class, args);
  }
}
