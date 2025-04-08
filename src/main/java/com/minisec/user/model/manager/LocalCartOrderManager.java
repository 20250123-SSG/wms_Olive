package com.minisec.user.model.manager;

import com.minisec.common.login.Login;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.OrderProductDto;
import com.minisec.user.model.dto.store.StoreDto;
import com.minisec.user.model.manager.helper.OrderDtoAssembler;
import com.minisec.user.model.manager.helper.OrderWrapper;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

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


    public void addOrder(StoreDto store, List<Integer> orderProductIndex) {
        List<OrderProductDto> orderProductListByStore = localUserCartList.get(store);

        try {
            for (int index : orderProductIndex) {
                OrderProductDto orderProductDto = orderProductListByStore.get(index);
                int quantity = orderProductDto.getQuantity();

                orderWrapper.addOrderFromCart(store, orderProductDto, quantity);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("존재하지 않는 장바구니 상품입니다.");
        }

        deleteOrderCartFromLocalCartList(localUserCartList, store, orderProductIndex);
    }


    private void deleteOrderCartFromLocalCartList(Map<StoreDto, List<OrderProductDto>> localUserCartList,
                                                  StoreDto storeDto,
                                                  List<Integer> orderProductIndex) {
        List<OrderProductDto> orderProductListByStore = localUserCartList.get(storeDto);
        List<OrderProductDto> toRemove = new ArrayList<>();

        for (int index : orderProductIndex) {
            toRemove.add(orderProductListByStore.get(index));
        }

        orderProductListByStore.removeAll(toRemove);

        if (orderProductListByStore.isEmpty()) {
            localUserCartList.remove(storeDto);
        }
    }


    public StoreDto getStoreByStoreName(String inputStoreName) {
        return localUserCartList.keySet().stream()
                .filter(store -> store.getStoreName().equals(inputStoreName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 존재하지 않는 가맹점입니다."));

    }


    public List<Integer> getOrderProductIndexList(String inputProductName) {
        Set<Integer> notDuplicationResult = new HashSet<>();

        try {
            for (String input : inputProductName.trim().split(",")) {
                notDuplicationResult.add(Integer.parseInt(input.trim()) - 1);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("장바구니 번호를 입력해주세요.");
        }
        return notDuplicationResult.stream()
                .sorted()
                .collect(Collectors.toList());
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
