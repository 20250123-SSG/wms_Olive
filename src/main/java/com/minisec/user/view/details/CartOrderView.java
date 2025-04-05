package com.minisec.user.view.details;

import com.minisec.common.login.Login;
import com.minisec.user.controller.CartController;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;
import com.minisec.user.model.helper.OrderWrapper;
import com.minisec.user.view.printer.CartDetailsPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class CartOrderView {
    private final Scanner sc = new Scanner(System.in);
    private final CartController cartController = new CartController();

    public void run(Login user) {
        OrderWrapper orderWrapper = new OrderWrapper();

        Map<StoreDto, List<OrderProductDto>> localUserCartList = cartController.selectAllCartDetailListByUserId(user);

        if(localUserCartList.isEmpty()) {
            System.out.println("[ 장바구니가 비어있습니다. ]");
            return;
        }
        CartDetailsPrinter.print(localUserCartList, orderWrapper.getOrderListByStore());
        System.out.println("""
                1. 모두 구매
                2. 선택 구매
                0. 뒤로가기
                >> 입력:""");

        switch (Integer.parseInt(sc.nextLine())){
            case 0: return;
            case 1: orderAllFromCart(user, localUserCartList); break;
            case 2: orderByChoice(user, localUserCartList); break;
        }
    }

    public void orderByChoice(Login user, Map<StoreDto, List<OrderProductDto>> localUserCartList){
        OrderWrapper orderWrapper = new OrderWrapper();

        while (true) {
            StoreDto storeDto = inputStore(localUserCartList);
            List<OrderProductDto> orderProductListByStore = localUserCartList.get(storeDto);
            List<Integer> orderProductIndex = inputOrderProductNum();

            for (int index : orderProductIndex) {
                OrderProductDto orderProductDto = orderProductListByStore.get(index);
                int quantity = orderProductDto.getQuantity();

                orderWrapper.addOrderFromCart(storeDto, orderProductDto, quantity);
            }
            deleteOrderCartFromLocalList(localUserCartList, storeDto, orderProductIndex);



            CartDetailsPrinter.print(localUserCartList, orderWrapper.getOrderListByStore());
            System.out.println("""
                    1. 구매 확정하기
                    2. 구매 목록에 상품 담기
                    0. 취소하기
                    >> 입력:""");
            int functionNum = Integer.parseInt(sc.nextLine());
            if(functionNum == 0){
                return;
            }
            if(functionNum == 1){
                break;
            }
        }
        List<OrderDto> orderList = new InputOrderMemoView().run(user,orderWrapper.getOrderListByStore());
        cartController.orderFromCart(user,orderList);
    }

    public void orderAllFromCart(Login user, Map<StoreDto, List<OrderProductDto>> localUserCartList){
        List<OrderDto> orderList = new InputOrderMemoView().run(user,localUserCartList);
        cartController.orderFromCart(user,orderList);
    }

    private List<Integer> inputOrderProductNum() {
        Set<Integer> notDuplicationResult = new HashSet<>();

        System.out.println("[ 구매할 상품의 장바구니 코드를 모두 입력하세요. ex)1,2,3,4 ]");
        for(String input : sc.nextLine().split(",")) {
            notDuplicationResult.add(Integer.parseInt(input.trim())-1);
        }
        return notDuplicationResult.stream().sorted().collect(Collectors.toList());
    }

    private StoreDto inputStore(Map<StoreDto, List<OrderProductDto>> userCartList) {
        System.out.println("[ 구매할 가맹점을 입력해주세요. ]");
        System.out.print(">>입력:");
        String storeName = sc.nextLine();

        for (StoreDto storeDto : userCartList.keySet()) {
            if (storeDto.getStoreName().equals(storeName)) {
                return storeDto;
            }
        }
        return null;
    }

    private void deleteOrderCartFromLocalList(Map<StoreDto, List<OrderProductDto>> localUserCartList,
                                              StoreDto storeDto,
                                              List<Integer> orderProductIndex) {
        orderProductIndex.sort(Comparator.reverseOrder());

        List<OrderProductDto> localUserCartListByStore = localUserCartList.get(storeDto);
        for(int index : orderProductIndex) {
            localUserCartListByStore.remove(index);
        }
        if(localUserCartListByStore.isEmpty()) {
            localUserCartList.remove(storeDto);
        }
    }

}
