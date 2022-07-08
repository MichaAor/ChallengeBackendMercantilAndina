package com.MAndina.challengeBack.Model.JPA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long pedidoCabecera;

    @OneToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int cantidad;
    private float importe;


    public PedidoDetalle(Long pedidoCabecera,Producto producto,int cantidad,float importe){
        this.pedidoCabecera = pedidoCabecera;
        this.producto = producto;
        this.cantidad = cantidad;
        this.importe = importe;
    }

}
