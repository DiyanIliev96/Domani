package com.iliev.domani.repository;

import com.iliev.domani.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findAllByUser_idAndOrderIsNull(Long user_id);
    void deleteAllByUser_Id(Long user_id);
    List<CartItem> findAllByOrder_Id(long order_id);
}
