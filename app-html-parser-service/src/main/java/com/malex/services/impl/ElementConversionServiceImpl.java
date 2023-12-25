package com.malex.services.impl;

import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import com.malex.services.AbstractParsingService;
import com.malex.services.ElementConversionService;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ElementConversionServiceImpl extends AbstractParsingService
    implements ElementConversionService {

  @Override
  public Flux<BillStatus> convertElementsToBillStatuses(List<Elements> elements) {
    List<BillStatus> statuses = elements.stream().map(this::buildBillStatusObj).toList();
    return Flux.fromIterable(statuses);
  }

  @Override
  public Flux<Bill> convertElementsToBills(List<Elements> elements) {
    List<Bill> bills = elements.stream().map(this::buildBillObj).toList();
    return Flux.fromIterable(bills);
  }

  private BillStatus buildBillStatusObj(Elements elements) {
    return BillStatus.builder()
        .data(getElementText(elements, "first -> data", Elements::first))
        .status(getElementText(elements, "last -> status", Elements::last))
        .build();
  }

  protected Bill buildBillObj(Elements tdElements) {
    return Bill.builder()
        .link(extractLink(tdElements))
        .number(extractNumber(tdElements))
        .registrationDate(extractRegistrationDate(tdElements))
        .name(extractName(tdElements))
        .build();
  }

  private String extractName(Elements elements) {
    return getElementText(elements, "bill name", Elements::last);
  }

  private String extractRegistrationDate(Elements elements) {
    return getElementText(elements, "bill registration date", element -> element.get(2));
  }

  private String extractNumber(Elements elements) {
    return getElementText(elements, "bill number", element -> element.get(1));
  }

  private String extractLink(Elements elements) {
    return Optional.of(elements.select(A_ELEMENT))
        .map(Elements::first)
        .map(element -> element.attr(HREF_ELEMENT))
        .orElseThrow(
            buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, HREF_ELEMENT + ", for bill link"));
  }

  private String getElementText(Elements elements, String tag, Function<Elements, Element> fn) {
    Element element = fn.apply(elements);
    return Optional.ofNullable(element)
        .map(Element::text)
        .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, tag));
  }
}
