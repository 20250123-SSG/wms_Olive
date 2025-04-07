package com.minisec.user.model.manager;

import com.minisec.common.login.Login;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;
import com.minisec.user.model.manager.helper.OrderDtoAssembler;
import com.minisec.user.model.manager.helper.OrderWrapper;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 장바구니 구매를 돕는 매니저
 */
public class LocalCartOrderManager {

    private final OrderDtoAssembler orderDtoAssembler = new OrderDtoAssembler();
    private final OrderWrapper orderWrapper = new OrderWrapper();

    @Getter
    private Map<StoreDto, List<OrderProductDto>> localUserCartList;

    public LocalCartOrderManager(Map<StoreDto, List<OrderProductDto>> localUserCartList) {
        this.localUserCartList = localUserCartList;
    }


    public boolean isCartEmpty() {
        return localUserCartList.isEmpty();
    }


    public StoreDto getStoreByStoreName(String inputStoreName) {
        for (StoreDto storeDto : localUserCartList.keySet()) {
            if (storeDto.getStoreName().equals(inputStoreName)) {
                return storeDto;
            }
        }
        return null;
    }

    public List<Integer> getOrderProductIndexList(String inputProductName) {
        Set<Integer> notDuplicationResult = new HashSet<>();

        for (String input : inputProductName.trim().split(",")) {
            notDuplicationResult.add(Integer.parseInt(input.trim()) - 1);
        }
        return notDuplicationResult.stream()
                .sorted()
                .collect(Collectors.toList());
    }


    public void addOrder(StoreDto store, List<Integer> orderProductIndex) {
        List<OrderProductDto> orderProductListByStore = localUserCartList.get(store);

        for (int index : orderProductIndex) {
            OrderProductDto orderProductDto = orderProductListByStore.get(index);
            int quantity = orderProductDto.getQuantity();

            orderWrapper.addOrderFromCart(store, orderProductDto, quantity);
        }
        deleteOrderCartFromLocalList(localUserCartList, store, orderProductIndex);
    }

    private void deleteOrderCartFromLocalList(Map<StoreDto, List<OrderProductDto>> localUserCartList,
                                              StoreDto storeDto,
                                              List<Integer> orderProductIndex) {
        orderProductIndex.sort(Comparator.reverseOrder());

        List<OrderProductDto> localUserCartListByStore = localUserCartList.get(storeDto);
        for (int index : orderProductIndex) {
            localUserCartListByStore.remove(index);
        }
        if (localUserCartListByStore.isEmpty()) {
            localUserCartList.remove(storeDto);
        }
    }


    public Map<StoreDto, List<OrderProductDto>> getOrderListByStore() {
        return orderWrapper.getOrderListByStore();
    }


    public List<OrderDto> getOrderListWhenAllFromCart(Login user) {
        return orderDtoAssembler.getOrderList(user, localUserCartList);
    }

    public List<OrderDto> getOrderListWhenChoice(Login user) {
        return orderDtoAssembler.getOrderList(user, orderWrapper.getOrderListByStore());
    }

}
