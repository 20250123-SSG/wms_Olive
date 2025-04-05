package com.minisec.user.model.helper;

import com.minisec.common.login.Login;
import com.minisec.user.common.OrderStatus;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDtoAssembler {

    public List<OrderDto> getOrderList(Login user, Map<StoreDto, List<OrderProductDto>> orderListByStore) {
        List<OrderDto> result = new ArrayList<>();

        for (Map.Entry<StoreDto, List<OrderProductDto>> entry : orderListByStore.entrySet()) {
            StoreDto store = entry.getKey();
            List<OrderProductDto> orderProductList = entry.getValue();

            validateUserAmount();
            OrderDto order = new OrderDto(
                    store,
                    user.getUserId(),
                    OrderStatus.ORDERABLE.getValue(),
                    orderProductList
            );
            result.add(order);
        }
        return result;
    }

    private void validateUserAmount() {

    }
}
