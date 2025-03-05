package com.tenpo.prueba.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "api_externa", url = "${external.service.url}")
@RequestMapping("/api/alfresco")
public interface IApiExterna {

    @GetMapping()
    ResponseEntity<Long> obtenerPorcentaje();

}
