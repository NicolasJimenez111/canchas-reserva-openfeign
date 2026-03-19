package com.clubdeportivo2.servicioreservas.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.clubdeportivo2.servicioreservas.model.dto.Cancha;

@FeignClient(
    name = "servicio-canchas",
    url = "${canchas.service.url:http://localhost:8081}",
    path = "/api/canchas"
)
public interface CanchaClient {

    @GetMapping("/{id}")
    Cancha obtenerCanchaPorId(@PathVariable("id") Long id);
}

