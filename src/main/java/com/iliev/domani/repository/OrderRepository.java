package com.iliev.domani.repository;

import com.iliev.domani.model.entity.OrderEntity;
import com.iliev.domani.model.enums.OrderStatusName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    List<OrderEntity> findAllByOrderStatusOrderByOrderDateTimeDesc(OrderStatusName orderStatus);
}
