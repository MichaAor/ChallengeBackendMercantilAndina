package com.MAndina.challengeBack.Model.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detalle {
    private String producto;
    private int cantidad;
}
