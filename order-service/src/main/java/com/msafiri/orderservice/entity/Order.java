package com.msafiri.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "`order_table`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;
    private String orderNumber;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

}
