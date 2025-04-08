package com.minisec.user.model.manager;


import com.minisec.common.login.Login;
import com.minisec.user.model.dto.store.StoreProductDto;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.OrderProductDto;
import com.minisec.user.model.dto.store.StoreDto;
import com.minisec.user.model.manager.helper.OrderDtoAssembler;
import com.minisec.user.model.manager.helper.OrderWrapper;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class LocalOrderManager {

    private final OrderDtoAssembler orderDtoAssembler = new OrderDtoAssembler();
    private final OrderWrapper orderWrapper = new OrderWrapper();

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


    public void addOrder(StoreDto store, StoreProductDto storeProduct, int quantity) {
        orderWrapper.addOrder(store, storeProduct, quantity);
        storeProduct.deleteLocalStoreProductQuantity(quantity);

        deleteProductFromCartList(storeProduct);
    }


    private void deleteProductFromCartList(StoreProductDto storeProduct) {
        if (storeProduct.getStoreProductQuantity() == 0) {
            String categoryName = storeProduct.getCategory();
            List<StoreProductDto> productByyCategory = storeProductByCategory.get(categoryName);

            productByyCategory.remove(storeProduct);

            storeProductList.remove(storeProduct);

            if (productByyCategory.isEmpty()) {
                storeProductByCategory.remove(categoryName);
            }
        }
    }


    public StoreDto getStoreByStoreId(Map<Integer, StoreDto> storeListByUniqueNumber, String inputStoreId) {
        try {
            StoreDto store = storeListByUniqueNumber.get(Integer.parseInt(inputStoreId));
            if (store == null) {
                throw new IllegalArgumentException("존재하지 않는 가맹점입니다.");
            }
            return store;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("가맹점 번호를 입력해주세요.");
        }
    }


    public StoreProductDto getOrderProduct(String inputProductId) {
        try {
            return storeProductList.get(Integer.parseInt(inputProductId.trim()) - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("존재하지 않는 상품 번호입니다.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("상품 번호를 입력해주세요.");
        }
    }


    public int getOrderQuantity(String inputQuantity) {
        int quantity = 0;

        try {
            quantity = Integer.parseInt(inputQuantity.trim());
            if (quantity <= 0) {
                throw new IllegalArgumentException("수량은 1개 이상 입력해주세요.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("수량은 숫자로 입력해주세요.");
        }
        return quantity;
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
