package com.MAndina.challengeBack.Service;

import com.MAndina.challengeBack.Model.Response.*;
import com.MAndina.challengeBack.Model.JPA.*;
import com.MAndina.challengeBack.Model.Request.*;
import com.MAndina.challengeBack.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PedidoService {
    @Autowired
    PedidoCRepo pCR;

    @Autowired
    ProductoService pS;

    @Autowired
    PedidoDRepo pDR;

/**MAPEOS**/

    private PedidoCabecera mappearPCabecera(PedidoRQ pRQ){
        PedidoCabecera pc = new PedidoCabecera(
                LocalDate.now(),
                pRQ.getDireccion(),
                pRQ.getEmail(),
                pRQ.getTelefono(),
                pRQ.getHorario());
        return pc;
    }

    private PedidoDetalle mappearPDetalle(Long pc, String idP, int cant){
        Producto producto = pS.getById(idP);
        PedidoDetalle detalle = new PedidoDetalle(pc,producto,cant,(producto.getPrecioUnitario() * cant));
        return detalle;
    }


/**SAVES*/
    public PedidoCabecera savePCabecera(PedidoRQ pRQ){
        PedidoCabecera pc = mappearPCabecera(pRQ);
        long idPc = pCR.save(pc).getId();
        pc.setId(idPc);
        return pc;
    }

    public void savePDetalle(Long pc, PedidoRQ pRQ){
        for(Detalle dRQ : pRQ.getDetalle()){
            PedidoDetalle pd = mappearPDetalle(pc, dRQ.getProducto(), dRQ.getCantidad());
             pDR.save(pd);
        }
    }

    public PedidoGral savePedido(PedidoRQ pRQ){
        PedidoCabecera pc = savePCabecera(pRQ);
        savePDetalle(pc.getId(),pRQ);
        PedidoGral pgr = getPedido(pc);
        return pgr;
    }


/**RESPONSE*/
///PARA REPONDER A CREACION
    public PedidoGral getPedido(PedidoCabecera pc){
        float total=0;
        int cant =0;
        boolean desc = false;
        PedidoGral pdg = new PedidoGral();
        pdg.setFecha(pc.getFecha());
        pdg.setDireccion(pc.getDireccion());
        pdg.setEmail(pc.getEmail());
        pdg.setTelefono(pc.getTelefono());
        pdg.setHorario(pc.getHorario());
        ArrayList<PedidoDetalle> detalles = pDR.getDetalleById(pc.getId());

        for(PedidoDetalle pDetalles: detalles){
            Producto producto = pS.getById(pDetalles.getProducto().getId());
            DetalleGral detaG = new DetalleGral(producto.getId(),
                    producto.getNombre(),
                    pDetalles.getCantidad(),
                    (pDetalles.getCantidad() * producto.getPrecioUnitario()));
            pdg.getDetalle().add(detaG);

            total += (pDetalles.getCantidad() * producto.getPrecioUnitario());
            cant += pDetalles.getCantidad();
        }

        if(cant > 3){
            total = total * 0.7f;
            desc = true;
        }

        pc.setTotal(total);
        pc.setDescuento(desc);
        pc.setEstado("PENDIENTE");
        pCR.save(pc);

        pdg.setTotal(total);
        pdg.setDescuento(desc);
        pdg.setEstado("PENDIENTE");
        return pdg;
    }


    public List<PedidoGral> getAllPDGRAL(LocalDate fecha){
        ArrayList<PedidoGral> pedidos = new ArrayList<>();
        ArrayList<PedidoCabecera> cabeceras = pCR.getByFecha(fecha);

        for(PedidoCabecera pc : cabeceras){
            pedidos.add(getPedido(pc));
        }
        return pedidos;
    }

}
