package com.minisec.user.model.manager;


import com.minisec.common.login.Login;
import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;
import com.minisec.user.model.manager.helper.OrderDtoAssembler;
import com.minisec.user.model.manager.helper.OrderWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 바로 구매 기능을 돕는 매니저
 */
@NoArgsConstructor
public class LocalOrderManager {

    private final OrderDtoAssembler orderDtoAssembler = new OrderDtoAssembler();
    private final OrderWrapper orderWrapper = new OrderWrapper();

    @Getter
    private List<StoreProductDto> storeProductList;

    public LocalOrderManager(List<StoreProductDto> storeProductList) {
        this.storeProductList = storeProductList;
    }


    public boolean isEmptyStoreProduct() {
        return storeProductList == null || storeProductList.isEmpty();
    }


    public StoreDto getStoreByStoreId(List<StoreDto> storeList, String inputStoreId) {
        return storeList.get(Integer.parseInt(inputStoreId.trim()) - 1);
    }

    public StoreProductDto getOrderProduct(String inputProductId) {
        return storeProductList.get(Integer.parseInt(inputProductId.trim()) - 1);
    }

    public int getOrderQuantity(String inputQuantity) {
        return Integer.parseInt(inputQuantity.trim());
    }


    public void addOrder(StoreDto store, StoreProductDto storeProduct, int quantity) {
        orderWrapper.addOrder(store, storeProduct, quantity);
        storeProduct.deleteLocalStoreProductQuantity(quantity);
    }

    public Map<StoreDto, List<OrderProductDto>> getOrderListByStore() {
        return orderWrapper.getOrderListByStore();
    }

    public List<OrderDto> getOrderList(Login user) {
        return orderDtoAssembler.getOrderList(user, orderWrapper.getOrderListByStore());
    }

}
