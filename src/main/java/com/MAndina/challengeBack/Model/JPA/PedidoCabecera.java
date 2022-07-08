package com.MAndina.challengeBack.Model.JPA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCabecera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private String direccion;
    private String email;
    private String telefono;
    private LocalTime horario;
    private float total;
    private boolean descuento;
    private String estado;


    @OneToMany(targetEntity = PedidoDetalle.class, cascade =  CascadeType.ALL)
    @JoinColumn(name= "pedidoCabecera", referencedColumnName= "id")
    private List<PedidoDetalle> detalles;

    public PedidoCabecera(LocalDate fecha, String direccion,String email, String telefono, LocalTime horario){
        this.fecha = fecha;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.horario = horario;
    }



}
