package com.clubdeportivo2.servicioreservas.services;

import com.clubdeportivo2.servicioreservas.model.Reserva;
import java.util.List;

public interface ReservaServices {

   List<Reserva> listarReservas();

    Reserva crearReserva(Reserva reserva);

    List<Reserva> buscarReservasPorCancha(Long canchaId);
}
