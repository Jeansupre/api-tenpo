package com.tenpo.prueba.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "api-externa", url = "${external.service.url}")
public interface IApiExterna {

    @GetMapping()
    ResponseEntity<Long> obtenerPorcentaje();

}
