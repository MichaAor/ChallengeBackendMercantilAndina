package com.MAndina.challengeBack.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleGral {
    private String producto;
    private String nombre;
    private int cantidad;
    private float importe;
}
