package com.malex.services;

import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import java.util.List;
import org.jsoup.select.Elements;
import reactor.core.publisher.Flux;

public interface ElementConversionService {

  Flux<BillStatus> convertElementsToBillStatuses(List<Elements> elements);

  Flux<Bill> convertElementsToBills(List<Elements> elements);
}
