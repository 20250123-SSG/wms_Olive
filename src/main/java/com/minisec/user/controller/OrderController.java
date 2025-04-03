package com.minisec.user.controller;

import com.minisec.common.login.Login;
import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.cart.CartDto;
import com.minisec.user.model.dto.order.*;
import com.minisec.user.service.CartService;
import com.minisec.user.service.OrderService;
import com.minisec.user.service.StoreService;
import com.minisec.user.view.printer.InsertStatusPrinter;
import com.minisec.user.view.printer.OrderDetailsPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderController {

    private final StoreService storeService = new StoreService();
    private final OrderService orderService = new OrderService();
    private final CartService cartService = new CartService();

    public List<StoreDto> selectStoreList() {
        return storeService.selectStoreList();
    }

    public List<StoreProductDto> selectStoreAllProductByStoreId(StoreDto storeDto) {
        return storeService.selectStoreAllProductByStoreId(storeDto.getStoreId());
    }

    /**
     * 1. store_detail UPDATE - 재고차감
     * 2. user - 금액차감
     * 3. user_order - 주문 추가
     * 4. user_order_detail - 주문 상세 추가
     * @param orderDtoList
     */


    /// 이 DTO 생성 로직을 따로 뺴고 좋을거같은데 어차피 CartController에서 사용해야됨
    public void insertOrder(List<OrderDto> orderDtoList) {
        List<StoreInventoryDeductionDto> storeInventoryDeductionList = new ArrayList<>(); ///1
        List<UserBalanceUpdateDto> userAmountDeductionList = new ArrayList<>(); ///2

        for (OrderDto orderDto : orderDtoList) {
            for(OrderProductDto orderProduct : orderDto.getOrderProducts()){
                /// 1. store_detail UPDATE - 재고차감
                int storeDetailId = orderProduct.getProduct().getStoreProductId(); //고유pk라 스토어아이디는 별도로 필요 없을듯
                int userOrderQuantity = orderProduct.getQuantity();
                storeInventoryDeductionList.add(new StoreInventoryDeductionDto(
                        storeDetailId,
                        userOrderQuantity
                ));
            }
            /// 2. user - 금액차감
            int userId = orderDto.getUserId();
            int totalPrice = orderDto.getTotalPrice();
            userAmountDeductionList.add(new UserBalanceUpdateDto(
                    userId,
                    Math.negateExact(totalPrice)
            ));
        }
        int insertResult = orderService.insertOrderList(new InsertOrderDto(
                storeInventoryDeductionList,
                userAmountDeductionList,
                orderDtoList
        ));

        if( insertResult == 0){
            InsertStatusPrinter.printInsertOrderList(false);
            return;
        }
        InsertStatusPrinter.printInsertOrderList(true);
        OrderDetailsPrinter.printList(orderDtoList);
    }

    public void insertCartList(Login user, Map<StoreDto, List<OrderProductDto>> orderListByStore) {
        List<CartDto> cartList = new ArrayList<>();

        for (Map.Entry<StoreDto, List<OrderProductDto>> entry : orderListByStore.entrySet()) {
            StoreDto storeDto = entry.getKey();
            List<OrderProductDto> orderProductDtoList = entry.getValue();

            for (OrderProductDto orderProductDto : orderProductDtoList) {
                cartList.add(new CartDto(
                        storeDto.getStoreId()
                        , user.getUserId()
                        , orderProductDto
                ));
            }
        }

        int insertResult = cartService.insertCartList(cartList);
        InsertStatusPrinter.printInsertCartList(insertResult == cartList.size());
    }

    public void selectOrderDetailListByUser(){

    }

}
