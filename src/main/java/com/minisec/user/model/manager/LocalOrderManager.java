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

import java.util.*;
import java.util.stream.Collectors;

/**
 * 바로 구매 기능을 돕는 매니저
 */
@NoArgsConstructor
public class LocalOrderManager {

    private final OrderDtoAssembler orderDtoAssembler = new OrderDtoAssembler();
    private final OrderWrapper orderWrapper = new OrderWrapper();

    @Getter
    private List<StoreProductDto> storeProductList = new ArrayList<>();
    private TreeMap<String, List<StoreProductDto>> storeProductByCategory;

    public LocalOrderManager(List<StoreProductDto> storeProductList) {
        groupingByCategoryForPrint(storeProductList);

        for (String category : storeProductByCategory.keySet()) {
            this.storeProductList.addAll(storeProductByCategory.get(category));
        }
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
        int quantity = 0;

        try {
            quantity = Integer.parseInt(inputQuantity.trim());
            if(quantity <= 0){
                throw new IllegalArgumentException("수량은 1개 이상 입력해주세요.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("수량은 숫자로 입력해주세요.");
        }
        return quantity;
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

    public Map<String, List<StoreProductDto>> getStoreProductByCategoryForPrint() {
        return storeProductByCategory;
    }

    private void groupingByCategoryForPrint(List<StoreProductDto> storeProductList) {
        Comparator<StoreProductDto> productComparator = Comparator.comparing(StoreProductDto::getProductName);
        storeProductByCategory = storeProductList.stream()
                .collect(Collectors.groupingBy(
                        StoreProductDto::getCategory,
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(productComparator)
                                        .collect(Collectors.toList())
                        )
                ));
    }


}
