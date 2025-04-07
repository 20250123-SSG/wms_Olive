package com.minisec.user.model.manager.helper;

import com.minisec.user.model.dto.store.StoreProductDto;
import com.minisec.user.model.dto.OrderProductDto;
import com.minisec.user.model.dto.store.StoreDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderWrapper {

    private final Map<StoreDto, List<OrderProductDto>> orderListByStore = new HashMap<>();

    public Map<StoreDto, List<OrderProductDto>> getOrderListByStore() {
        return orderListByStore;
    }



    public OrderProductDto addOrder(StoreDto store, StoreProductDto product, int quantity) {
        OrderProductDto orderProduct = new OrderProductDto(product,
                quantity,
                product.getStoreProductPriceAfterDiscount() * quantity);

        List<OrderProductDto> orderProductList = checkExistStore(store);

        return checkExistProduct(quantity, orderProductList, orderProduct);
    }


    public void addOrderFromCart(StoreDto store, OrderProductDto product, int quantity) {
        List<OrderProductDto> orderProductList = checkExistStore(store);
        checkExistProduct(quantity, orderProductList, product);
    }


    private List<OrderProductDto> checkExistStore(StoreDto store) {
        if (orderListByStore.containsKey(store)) {
            return orderListByStore.get(store);
        }
        List<OrderProductDto> orderProductList = new ArrayList<>();
        orderListByStore.put(store, orderProductList);

        return orderProductList;
    }


    private OrderProductDto checkExistProduct(int quantity,
                                              List<OrderProductDto> orderProductList,
                                              OrderProductDto orderProduct) {

        if (orderProductList.contains(orderProduct)) {
            orderProduct = orderProductList.get(orderProductList.indexOf(orderProduct));
            orderProduct.updateQuantity(quantity);
            return orderProduct;
        }

        orderProductList.add(orderProduct);
        return orderProduct;
    }

}
