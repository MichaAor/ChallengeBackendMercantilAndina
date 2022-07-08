package com.MAndina.challengeBack.Model.JPA;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto{
    @Id
    private String id;
    private String nombre;
    private String descripcionCorta;
    private String descripcionLarga;
    private float precioUnitario;
}
