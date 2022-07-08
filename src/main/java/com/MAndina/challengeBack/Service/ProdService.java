package com.MAndina.challengeBack.Service;

import com.MAndina.challengeBack.Model.JPA.Producto;
import com.MAndina.challengeBack.Repository.ProdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdService {
    @Autowired
    ProdRepo pR;

    public List<Producto> getAll(){
        return  pR.findAll();
    }

    public Optional<Producto> getById(String id){
        Optional<Producto> pr = pR.findById(id);
        return pr;
    }

    public void save(Producto producto){
        pR.save(producto);
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
