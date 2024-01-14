package com.malex.jsoup;

import com.malex.model.base.Bill;
import com.malex.model.base.BillStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class HtmlPageParsingService extends AbstractParsingService {

  private HtmlElementParsingService conversionService;

  public Flux<BillStatus> processBillStatus(String html) {
    Document document = Jsoup.parse(html);
    Element tableElement = parseNavTab1Element(document, Element::firstElementChild);
    Element tbody = parseTBodyElement(tableElement, Elements::first);
    Elements trElements = parseTrElements(tbody);
    List<Elements> tdElements = parseTdTableElements(trElements);
    return conversionService.convertElementsToBillStatuses(tdElements);
  }

  public Flux<Bill> processBillSearchResult(String html) {
    Document document = Jsoup.parse(html);
    Elements elements = parseEuroBoxElements(document);
    List<Elements> list = parseTableElements(elements);
    return conversionService.convertElementsToBills(list);
  }
}
