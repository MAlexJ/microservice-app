package com.malex.services.impl;

import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import com.malex.models.base.ResponseState;
import com.malex.models.response.HtmlParserResponse;
import com.malex.models.response.ProxyResponse;
import com.malex.services.AbstractParsingService;
import com.malex.services.ElementConversionService;
import com.malex.services.HtmlPageParsingService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class HtmlPageParsingServiceImpl extends AbstractParsingService
    implements HtmlPageParsingService {

  private ElementConversionService conversionService;

  @Override
  public Flux<BillStatus> processBillStatus(String html) {
    Document document = Jsoup.parse(html);
    Element tableElement = parseNavTab1Element(document, Element::firstElementChild);
    Element tbody = parseTBodyElement(tableElement, Elements::first);
    Elements trElements = parseTrElements(tbody);
    List<Elements> tdElements = parseTdTableElements(trElements);
    return conversionService.convertElementsToBillStatuses(tdElements);
  }

  @Override
  public Flux<Bill> processBillSearchResult(String html) {
    Document document = Jsoup.parse(html);
    Elements elements = parseEuroBoxElements(document);
    List<Elements> list = parseTableElements(elements);
    return conversionService.convertElementsToBills(list);
  }

  /** Find Elements that match the supplied XPath expression */
  @Override
  public Mono<HtmlParserResponse> processXPathExpressions(ProxyResponse response, String xpath) {
    if (response.getState() == ResponseState.FALLBACK) {
      return Mono.fromSupplier(() -> new HtmlParserResponse("", ResponseState.FALLBACK));
    }
    return Mono.fromSupplier(
        () -> {
          Document document = Jsoup.parse(response.getHtmlAsText());
          Elements elements = document.selectXpath(xpath);
          return new HtmlParserResponse(elements.html(), ResponseState.SERVICE);
        });
  }
}
