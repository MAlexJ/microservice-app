package com.malexj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1")
public class ApiRestController {

    @GetMapping("/subscriptions")
    public Mono<ResponseEntity<String>> appSubscriptions() {
        String resp = "[\n" + "    {\n" + "        \"id\": \"653189d3f5e5120cdb241561\",\n" + "        \"user\": {\n" + "            \"username\": \"malex\",\n" + "            \"email\": \"hsh@mz.ck2k\"\n" + "        },\n" + "        \"bills\": null,\n" + "        \"createdDate\": null,\n" + "        \"active\": true\n" + "    },\n" + "    {\n" + "        \"id\": \"65318a7df5e5120cdb241562\",\n" + "        \"user\": {\n" + "            \"username\": \"malex\",\n" + "            \"email\": \"hsh@mz.dssddsdssd\"\n" + "        },\n" + "        \"bills\": null,\n" + "        \"createdDate\": null,\n" + "        \"active\": true\n" + "    }\n" + "]";
        return Mono.just(ResponseEntity.ok(resp));
    }


    @GetMapping("/bills/{number}")
    public Mono<ResponseEntity<String>> searchBillByNumber(@PathVariable String number) {
        if (number.isEmpty()) {
            throw new RuntimeException("bill number should be not null or empty!");
        }
        String resp = "{\n" + "    \"bills\": [\n" + "        {\n" + "            \"link\": \"https://itd.rada.gov.ua/billInfo/Bills/Card/42664\",\n" + "            \"name\": \"Проект Закону про внесення змін до статті 23 Закону України \\\"Про мобілізаційну підготовку та мобілізацію\\\"\",\n" + "            \"number\": \"9672\",\n" + "            \"registrationDate\": \"2023-09-04\"\n" + "        },\n" + "        {\n" + "            \"link\": \"https://itd.rada.gov.ua/billInfo/Bills/Card/42700\",\n" + "            \"name\": \"Проект Закону про внесення змін до статті 23 Закону України \\\"Про мобілізаційну підготовку та мобілізацію\\\"\",\n" + "            \"number\": \"9672-1\",\n" + "            \"registrationDate\": \"2023-09-05\"\n" + "        },\n" + "        {\n" + "            \"link\": \"https://itd.rada.gov.ua/billInfo/Bills/Card/42755\",\n" + "            \"name\": \"Проект Закону про внесення змін до статті 23 Закону України \\\"Про мобілізаційну підготовку та мобілізацію\\\"\",\n" + "            \"number\": \"9672-2\",\n" + "            \"registrationDate\": \"2023-09-14\"\n" + "        },\n" + "        {\n" + "            \"link\": \"https://itd.rada.gov.ua/billInfo/Bills/Card/42806\",\n" + "            \"name\": \"Проект Закону про внесення змін до статті 23 Закону України \\\"Про мобілізаційну підготовку та мобілізацію\\\"\",\n" + "            \"number\": \"9672-3\",\n" + "            \"registrationDate\": \"2023-09-19\"\n" + "        }\n" + "    ]\n" + "}";
        return Mono.just(ResponseEntity.ok(resp));
    }

    @PostMapping("/bills")
    public Mono<ResponseEntity<String>> subscribe(@RequestBody String bills) {
        log.info(">>>>> Request: " + bills);
        return Mono.empty();
    }

}