package com.msafiri.orderservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order_item_table`")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderItemId;
    private String status;
    private long customerId;
    private long productId;
    private int quantity;
    private BigDecimal price;
}
