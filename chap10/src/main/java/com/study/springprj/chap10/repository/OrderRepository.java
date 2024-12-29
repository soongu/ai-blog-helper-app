package com.study.springprj.chap10.repository;

import com.study.springprj.chap10.entity.Order;
import com.study.springprj.chap10.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 고객명으로 주문 검색
    List<Order> findByCustomerNameContaining(String name);

    // 특정 금액 이상의 주문 검색
    List<Order> findByAmountGreaterThan(int amount);

    // 특정 기간의 완료된 주문 검색
    List<Order> findByStatusAndOrderDateBetween(
            OrderStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    // 고객별 주문 횟수 계산
    long countByCustomerName(String customerName);

    @Query("SELECT o FROM Order o " +
            "WHERE o.status = :status " +
            "AND o.amount >= :minAmount " +
            "ORDER BY o.orderDate DESC")
    List<Order> searchOrders(
            @Param("status") OrderStatus status,
            @Param("minAmount") int minAmount
    );

    @Query(value =
            "SELECT status, COUNT(*) as order_count, SUM(amount) as total_amount " +
                    "FROM orders " +
                    "GROUP BY status",
            nativeQuery = true)
    List<Object[]> getOrderStatisticsByStatus();
}

