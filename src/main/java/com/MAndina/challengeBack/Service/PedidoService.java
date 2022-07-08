package com.MAndina.challengeBack.Service;

import com.MAndina.challengeBack.Model.Response.DetalleGral;
import com.MAndina.challengeBack.Model.JPA.PedidoCabecera;

import com.MAndina.challengeBack.Model.JPA.PedidoDetalle;
import com.MAndina.challengeBack.Model.JPA.Producto;
import com.MAndina.challengeBack.Model.Response.PedidoGral;
import com.MAndina.challengeBack.Model.Request.Detalle;
import com.MAndina.challengeBack.Model.Request.PedidoRQ;
import com.MAndina.challengeBack.Repository.PDetalleRepo;
import com.MAndina.challengeBack.Repository.PCabeceraRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    PCabeceraRepo pCR;

    @Autowired
    ProdService pS;

    @Autowired
    PDetalleRepo pDR;

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
        Producto producto = pS.getById(idP).get();
        PedidoDetalle detalle = new PedidoDetalle(pc,producto,cant,(producto.getPrecioUnitario() * cant));
        return detalle;
    }


/**SAVES*/
    public PedidoCabecera savePedCabecera(PedidoRQ pRQ){
        PedidoCabecera pc = mappearPCabecera(pRQ);
        long idPc = pCR.save(pc).getId();
        pc.setId(idPc);
        return pc;
    }

    public void savePedDetalle(Long pc,PedidoRQ pRQ){
        for(Detalle dRQ : pRQ.getDetalle()){
            PedidoDetalle pd = mappearPDetalle(pc, dRQ.getProducto(), dRQ.getCantidad());
             pDR.save(pd);
        }
    }

    public PedidoGral savePedido(PedidoRQ pRQ){
        PedidoCabecera pc = savePedCabecera(pRQ);
        savePedDetalle(pc.getId(),pRQ);
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
        for(PedidoDetalle deta: detalles){
            Producto producto = pS.getById(deta.getProducto().getId()).get();
            DetalleGral detaG = new DetalleGral(producto.getId(),producto.getNombre(),deta.getCantidad(),(deta.getCantidad() * producto.getPrecioUnitario()));
            pdg.getDetalles().add(detaG);
            total += (deta.getCantidad() * producto.getPrecioUnitario());
            cant += deta.getCantidad();
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
