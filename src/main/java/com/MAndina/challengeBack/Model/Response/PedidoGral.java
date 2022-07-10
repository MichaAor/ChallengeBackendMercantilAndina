package com.MAndina.challengeBack.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoGral {
    private LocalDate fecha;
    private String direccion;
    private String email;
    private String telefono;
    private LocalTime horario;
    private ArrayList<DetalleGral> detalle = new ArrayList<>();
    private float total;
    private boolean descuento;
    private String estado;
}
