package com.MAndina.challengeBack.Repository;

import com.MAndina.challengeBack.Model.JPA.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepo extends JpaRepository<Producto,String> {
}
