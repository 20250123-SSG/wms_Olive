package com.minisec.user.model.dao.order;

import com.minisec.user.model.dto.order.OrderDto;

import java.util.List;

public interface OrderDetailDao {
    int insertOrderDetailList(List<OrderDto> orderList);

    List<OrderDto> selectOrderDetailListByUser(int userId);
}
