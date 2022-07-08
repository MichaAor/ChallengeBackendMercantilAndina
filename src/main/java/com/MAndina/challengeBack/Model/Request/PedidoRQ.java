package com.MAndina.challengeBack.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRQ {
    private String direccion;
    private String email;
    private String telefono;
    private LocalTime horario;
    private ArrayList<Detalle> detalle = new ArrayList<>();
}
