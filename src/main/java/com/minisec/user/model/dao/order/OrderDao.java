package com.minisec.user.model.dao.order;

import com.minisec.user.model.dto.order.OrderDetailFilterDto;
import com.minisec.user.model.dto.order.OrderDto;

import java.util.List;

public interface OrderDao {

    List<OrderDto> selectAllOrderListByFilter(OrderDetailFilterDto filter);

    List<OrderDto> selectAllOrderDetailListByFilter(OrderDetailFilterDto filter);

    int insertOrder(OrderDto order);

}
