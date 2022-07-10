package com.MAndina.challengeBack;

import com.MAndina.challengeBack.Model.Request.Detalle;
import com.MAndina.challengeBack.Model.Request.PedidoRQ;
import com.MAndina.challengeBack.Model.Response.PedidoGral;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestInt {
    @Autowired
    private TestRestTemplate client;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testCrearPedido(){
        PedidoRQ pRQ = generarPedido();
        ResponseEntity<PedidoGral> resp = client.postForEntity("/pedidos",pRQ,PedidoGral.class);
        PedidoGral json = resp.getBody();
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        System.err.println(json);
    }

    @Test
    @Order(2)
    void testCrearPedidoConDesc(){
        PedidoRQ pRQ = generarPedidoConDesc();
        ResponseEntity<PedidoGral> resp = client.postForEntity("/pedidos",pRQ,PedidoGral.class);
        PedidoGral json = resp.getBody();
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        System.err.println(json);
    }

    @Test
    @Order(3)
    void testRecuperarPorFecha() throws JsonProcessingException {
        ResponseEntity<String> response = client.getForEntity("/pedidos?fecha=2022-07-10", String.class);
        String json = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(json);
        System.err.println(json);
    }

/**GENERADORES*/
    private PedidoRQ generarPedido(){
        ArrayList<Detalle> detalle = new ArrayList<>();
        detalle.add(new Detalle("89efb206-2aa6-4e21-8a23-5765e3de1f31",1));
        detalle.add(new Detalle("e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1",1));
        PedidoRQ rq = new PedidoRQ("Dorton Road 80"
                , "tsayb@opera.com"
                ,"(0351) 48158101"
                , LocalTime.parse("21:00")
                ,detalle);
        return rq;
    }

    private PedidoRQ generarPedidoConDesc(){
        ArrayList<Detalle> detalle = new ArrayList<>();
        detalle.add(new Detalle("89efb206-2aa6-4e21-8a23-5765e3de1f31",2));
        detalle.add(new Detalle("e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1",2));
        PedidoRQ rq = new PedidoRQ("Dorton Road 80"
                , "tsayb@opera.com"
                ,"(0351) 48158101"
                , LocalTime.parse("21:00")
                ,detalle);
        return rq;
    }

}
