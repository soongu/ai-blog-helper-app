package com.study.springprj.chap10.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String itemName;
    private int amount; // 주문 금액

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    @PrePersist
    void prePersist() {
        this.orderDate = LocalDateTime.now();
    }

    // 정적 팩토리 메서드
    public static Order createOrder(String customerName, String itemName, int amount, OrderStatus status) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setItemName(itemName);
        order.setStatus(status);
        order.setAmount(amount);
        return order;
    }
}
