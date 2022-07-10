package com.MAndina.challengeBack.Repository;


import com.MAndina.challengeBack.Model.JPA.PedidoCabecera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface PedidoCRepo extends JpaRepository<PedidoCabecera,Long> {
    ArrayList<PedidoCabecera> getByFecha(LocalDate fecha);
}
