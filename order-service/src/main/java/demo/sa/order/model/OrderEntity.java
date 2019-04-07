package demo.sa.order.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "orders")
public class OrderEntity {

    @Id
    private String orderId;
    private List<Address> addresses;
    private Float totalPrice;
    private String currency;
    private List<Product> products;
    private List<OrderItem> orderItems;


}