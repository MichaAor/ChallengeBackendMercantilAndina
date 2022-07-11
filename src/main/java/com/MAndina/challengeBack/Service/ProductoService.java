package com.MAndina.challengeBack.Service;

import com.MAndina.challengeBack.Model.JPA.Producto;
import com.MAndina.challengeBack.Repository.ProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    ProductoRepo pR;

    public List<Producto> getAll(){
        return  pR.findAll();
    }

    public Producto getById(String id){
        return pR.findById(id).orElseThrow();
    }

    public Producto save(Producto producto){
        return pR.save(producto);
    }

    public boolean exist(String id){
        return pR.existsById(id);
    }

    public boolean update(Producto producto){
        if(this.exist(producto.getId())) {
            pR.save(producto);
            return true;
        }
        return false;
    }

    public boolean delete(String id){
        if(this.exist(id)) {
            pR.deleteById(id);
            return true;
        }
        return false;
    }
}
