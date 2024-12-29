package com.study.springprj.chap10.repository;

import com.study.springprj.chap10.entity.Order;
import com.study.springprj.chap10.entity.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        // 주문 데이터 초기화
        orderRepository.save(Order.createOrder("김철수", "노트북", 1500000, OrderStatus.PENDING));
        orderRepository.save(Order.createOrder("이영희", "스마트폰", 800000, OrderStatus.PAID));
        orderRepository.save(Order.createOrder("박민수", "키보드", 100000, OrderStatus.COMPLETED));
        orderRepository.save(Order.createOrder("김철수", "모니터", 300000, OrderStatus.CANCELLED));
        orderRepository.save(Order.createOrder("이영희", "마우스", 50000, OrderStatus.COMPLETED));
    }

    @Test
    @DisplayName("고객명으로 주문 검색 테스트")
    void findByCustomerNameContainingTest() {
        // when
        List<Order> orders = orderRepository.findByCustomerNameContaining("김철수");

        // then
        assertThat(orders).hasSize(2);
        assertThat(orders).extracting("customerName").containsOnly("김철수");
    }

    @Test
    @DisplayName("특정 금액 이상의 주문 검색 테스트")
    void findByAmountGreaterThanTest() {
        // when
        List<Order> orders = orderRepository.findByAmountGreaterThan(100000);

        // then
        assertThat(orders).hasSize(3);
        assertThat(orders).extracting("itemName").contains("노트북", "스마트폰", "모니터");
    }

    @Test
    @DisplayName("특정 기간의 완료된 주문 검색 테스트")
    void findByStatusAndOrderDateBetweenTest() {
        // given
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        // when
        List<Order> orders = orderRepository.findByStatusAndOrderDateBetween(OrderStatus.COMPLETED, startDate, endDate);

        // then
        assertThat(orders).hasSize(2);
        assertThat(orders).extracting("status").containsOnly(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("고객별 주문 횟수 계산 테스트")
    void countByCustomerNameTest() {
        // when
        long count = orderRepository.countByCustomerName("이영희");

        // then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("주문 검색: 상태와 최소 금액 조건")
    void searchOrdersTest() {
        // given
        OrderStatus status = OrderStatus.COMPLETED;
        int minAmount = 60000;

        // when
        List<Order> orders = orderRepository.searchOrders(status, minAmount);

        // then
        assertThat(orders).hasSize(1); // 검색된 주문이 2개여야 함
        assertThat(orders.get(0).getAmount()).isGreaterThanOrEqualTo(minAmount);
        assertThat(orders.get(0).getStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("주문 상태별 통계 조회")
    void getOrderStatisticsByStatusTest() {
        // when
        List<Object[]> statistics = orderRepository.getOrderStatisticsByStatus();

        // then
        assertThat(statistics).hasSize(4); // 4가지 상태별로 결과 존재

        for (Object[] row : statistics) {
            OrderStatus status = OrderStatus.valueOf(row[0].toString());
            long orderCount = ((Number) row[1]).longValue();
            long totalAmount = ((Number) row[2]).longValue();

            System.out.printf("Status: %s, Order Count: %d, Total Amount: %d%n",
                    status, orderCount, totalAmount);

            // 간단한 검증
            if (status == OrderStatus.COMPLETED) {
                assertThat(orderCount).isEqualTo(2);
                assertThat(totalAmount).isEqualTo(150000);
            }
        }
    }

}