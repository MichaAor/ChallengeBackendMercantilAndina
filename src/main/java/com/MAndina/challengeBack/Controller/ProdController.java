package com.MAndina.challengeBack.Controller;

import com.MAndina.challengeBack.Model.JPA.Producto;
import com.MAndina.challengeBack.Service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProdController {
    @Autowired
    ProdService pS;

    @GetMapping
    public ResponseEntity<List<Producto>> getAll(){
        return ResponseEntity.ok(pS.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> readOne(@PathVariable(value = "id") String id){
        Optional<Producto> producto = pS.getById(id);
        if(!(producto.isEmpty())){
            return ResponseEntity.ok(producto.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody Producto producto){
        try {
            pS.save(producto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(((SQLException) e.getRootCause()).getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Integer> update(@PathVariable(value = "id") String id,@RequestBody Producto producto){
            producto.setId(id);
            if(producto.getId() == null){
                return ResponseEntity.badRequest().build();
            }
            if(pS.update(producto)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable(value = "id") String id){
        if(pS.delete(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
