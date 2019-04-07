package demo.sa.apigateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.sa.order.model.dto.OrderDto;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;


public class OrdersIT extends ApiGatewayApplicationIntegrationTests {

    private WebTestClient webClient;

    private String baseUri;

    @LocalServerPort
    private int port = 0;



    @Before
    public void setUp()  {
        baseUri = "http://localhost:" + port;
        this.webClient = WebTestClient.bindToServer()
                .responseTimeout(Duration.ofSeconds(10)).baseUrl(baseUri).build();
    }

    @Test
    public void orders_handlers() throws IOException, URISyntaxException {
        URI uri = getClass().getClassLoader()
                .getResource("orders.post.json").toURI();

        OrderDto orderDto = new ObjectMapper()
                .readValue(uri.toURL(),
                        OrderDto.class);

        webClient.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(orderDto))
                .exchange()
                .expectStatus().isOk();

        webClient.get().uri("/orders").exchange().expectStatus().isOk();
    }
}
