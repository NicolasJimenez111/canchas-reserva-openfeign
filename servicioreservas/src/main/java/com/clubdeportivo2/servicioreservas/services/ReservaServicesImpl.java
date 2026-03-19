package com.clubdeportivo2.servicioreservas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubdeportivo2.servicioreservas.clients.CanchaClient;
import com.clubdeportivo2.servicioreservas.model.Reserva;
import com.clubdeportivo2.servicioreservas.model.dto.Cancha;
import com.clubdeportivo2.servicioreservas.repository.ReservaRepository;
import java.util.List;



@Service
public class ReservaServicesImpl implements ReservaServices {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CanchaClient canchaClient;
    
    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    
    /* 
    @Override
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }*/


    @Override
    public Reserva crearReserva(Reserva reserva) {

        Cancha cancha;
        try {
            cancha = canchaClient.obtenerCanchaPorId(reserva.getCanchaId());
        } catch (Exception e) {
            throw new RuntimeException("La cancha no existe o el servicio de canchas no está disponible", e);
        }

        if (cancha == null) {
            throw new RuntimeException("La cancha no existe");
        }

        return reservaRepository.save(reserva);
    }

   

    @Override
    public List<Reserva> buscarReservasPorCancha(Long canchaId) {
        return reservaRepository.findByCanchaId(canchaId);
    }

    

}
