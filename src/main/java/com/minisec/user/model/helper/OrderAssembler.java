package com.minisec.user.model.helper;

import com.minisec.common.login.Login;
import com.minisec.user.common.OrderStatus;
import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/// TODO StoreProductDto productId로 storeProductDto에서 수량 검사
//검증
//근데 상품이 한하나 있는지 검증하기 위해서는 그니까 직므 추가하려는 상품이 사용자가 추가한 상품보다 수량이 ㄱㅊ은지 ㅎdb에서 2차 검증하기위해서는 이 로직은 서비스여야한다.


public class OrderAssembler {
    private StoreProductDto storeProductDto; //수량 추가 예외처리할때 필요

    public List<OrderDto> getOrderList(Login user, Map<StoreDto, List<OrderProductDto>> orderListByStore) {
        List<OrderDto> result = new ArrayList<>();

        for(Map.Entry<StoreDto, List<OrderProductDto>> entry : orderListByStore.entrySet()) {
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

    private void validateUserAmount(){

    }
}
