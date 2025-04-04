package com.minisec.user.model.dao.order;

import com.minisec.user.model.dto.order.OrderDetailFilterDto;
import com.minisec.user.model.dto.order.OrderDto;

import java.util.List;

public interface OrderDao {

    int insertOrder(OrderDto order);

    List<OrderDto> selectAllOrderDetailListByFilter(OrderDetailFilterDto filter);

}
