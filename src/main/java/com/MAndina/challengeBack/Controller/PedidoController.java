package com.MAndina.challengeBack.Controller;

import com.MAndina.challengeBack.Model.JPA.PedidoCabecera;
import com.MAndina.challengeBack.Model.Request.PedidoRQ;
import com.MAndina.challengeBack.Model.Response.PedidoGral;
import com.MAndina.challengeBack.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    PedidoService pS;

    @GetMapping
    public ResponseEntity<List<PedidoGral>> getByFecha(@RequestParam String fecha){
        LocalDate fechaB = LocalDate.parse(fecha);
        return ResponseEntity.ok(pS.getAllPDGRAL(fechaB));
    }


    @PostMapping
    public ResponseEntity<PedidoGral> create(@RequestBody PedidoRQ pRQ){
        try {
            PedidoGral rta = pS.savePedido(pRQ);
            return new ResponseEntity<>(rta,HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
