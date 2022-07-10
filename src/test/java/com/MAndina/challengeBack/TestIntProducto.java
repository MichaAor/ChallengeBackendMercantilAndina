package com.MAndina.challengeBack;

import com.MAndina.challengeBack.Model.JPA.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestIntProducto {

    @Autowired
    private TestRestTemplate client;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testRecuperarTodos(){
        ResponseEntity<String> response = client.getForEntity("/productos", String.class);
        String json = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(json);
        System.err.println(json);
    }

    @Test
    @Order(2)
    void testRecuperarPorId() {
        ResponseEntity<Producto> response = client.getForEntity("/productos/89efb206-2aa6-4e21-8a23-5765e3de1f31", Producto.class);
        Producto json = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(json);
        System.err.println(json);
    }

    @Test
    @Order(3)
    void testCrearProducto(){
        Producto pr = generarProducto();
        ResponseEntity<Integer> response = client
                .postForEntity("/productos",pr,Integer.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(4)
    void testActualizarProducto(){
        ResponseEntity<Producto> response = client.getForEntity("/productos/22efb987-4aa1-6e79-2a88-4444e5de3f98", Producto.class);
        Producto json = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(json);
        System.err.println(json);

        Producto pr = generarProductoModificado();
        client.put("/productos/22efb987-4aa1-6e79-2a88-4444e5de3f98",pr);

        response = client.getForEntity("/productos/22efb987-4aa1-6e79-2a88-4444e5de3f98", Producto.class);

        json = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(json);
        System.err.println(json);
    }

    @Test
    @Order(5)
    void testBorrarProducto(){
        ResponseEntity<Producto[]> respuesta = client.getForEntity("/productos",Producto[].class);
        List<Producto> prs = Arrays.asList(respuesta.getBody());
        assertEquals(3,prs.size());

        client.delete("/productos/22efb987-4aa1-6e79-2a88-4444e5de3f98");

        respuesta = client.getForEntity("/productos",Producto[].class);
        prs = Arrays.asList(respuesta.getBody());
        assertEquals(2,prs.size());

        ResponseEntity<Producto> rtaP = client
                .getForEntity("/productos/22efb987-4aa1-6e79-2a88-4444e5de3f98",Producto.class);
        assertEquals(HttpStatus.NOT_FOUND,rtaP.getStatusCode());
        assertFalse(rtaP.hasBody());
    }


/**DEMOS*/

    private Producto generarProducto(){
        Producto pr = new Producto("22efb987-4aa1-6e79-2a88-4444e5de3f98"
                ,"Fugazza"
                ,"Pizza de cebolla y aceitunas negras"
                ,"Mozzarella, cebolla y aceitunas negras"
                ,750.00f);
        return pr;
    }

    private Producto generarProductoModificado(){
        Producto pr = new Producto("22efb987-4aa1-6e79-2a88-4444e5de3f98"
                ,"Fugazza"
                ,"Pizza de cebolla y aceitunas negras"
                ,"Mozzarella, cebolla y aceitunas negras"
                ,900.00f);
        return pr;
    }

}


