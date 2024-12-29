package com.study.springprj.chap10.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;    // 고객명
    private String itemName;        // 상품명
    private int amount;            // 주문금액

    @Enumerated(EnumType.STRING)
    private OrderStatus status;    // 주문상태
    private LocalDateTime orderDate;// 주문일시

    @PrePersist
    void prePersist() {
        orderDate = LocalDateTime.now();
    }

    //정적 팩토리 메서드
    public static Order createOrder(String customerName, String itemName, int amount, OrderStatus orderStatus) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setItemName(itemName);
        order.setAmount(amount);
        order.setStatus(orderStatus);
        return order;
    }
}
