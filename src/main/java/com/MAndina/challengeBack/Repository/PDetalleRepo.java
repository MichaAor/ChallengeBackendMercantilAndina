package com.MAndina.challengeBack.Repository;

import com.MAndina.challengeBack.Model.JPA.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PDetalleRepo extends JpaRepository<PedidoDetalle,Long> {
    @Query(value = "SELECT * FROM pedido_detalle  WHERE pedido_cabecera = ?1", nativeQuery = true)
    public ArrayList<PedidoDetalle> getDetalleById(@Param("1") Long idCabecera);
}
