package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.OrderController;
import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;
import com.minisec.user.model.helper.OrderWrapper;
import com.minisec.user.view.details.InputOrderMemoView;
import com.minisec.user.view.printer.OrderDetailsPrinter;
import com.minisec.user.view.printer.ShopProductPrinter;
import com.minisec.user.view.printer.StoreListPrinter;

import java.util.*;

/**
 * 구매
 * 1. 가맹점 입력받기
 * 2. 가맹점 물품 출력하기
 * <p>
 * 3. 상품 번호 입력받기
 * 4. 상품 디테일 띄우기
 * 5. 구매할 수량 입력받기
 * 수량 유효검사
 * 6. 구매or담기or종료 입력받기
 * 7. 처리
 */

/**
 * 수량을 줄일때 상점의 상품 수량을 줄여야됨
 */
public class OrderView {
    private final Scanner sc = new Scanner(System.in);

    private final OrderController orderController = new OrderController();

    public void run(Login user) {
        OrderWrapper orderWrapper = new OrderWrapper();

        StoreDto store = inputStore();
        List<StoreProductDto> storeProductList = orderController.selectStoreAllProductByStoreId(store);

        if (storeProductList == null || storeProductList.isEmpty()) {
            System.out.println("해당 가맹점에 판매하는 상품이 존재하지 않습니다.");
            return;
        }
        while (true) {
            StoreProductDto product = inputOrderProduct(store, storeProductList);
            int quantity = inputOrderQuantity(product);

            /// 구매한 수량만큼 가맹점 재고 업데이트
            OrderProductDto orderProduct = orderWrapper.addOrder(store, product, quantity);
            orderProduct.getProduct().deleteStoreProductQuantity(quantity);

            int orderFunctionNum = inputOrderFunction();
            switch (orderFunctionNum) {
                case 0:
                    addToCart(user, orderWrapper.getOrderListByStore());
                    return;
                case 1:
                    purchase(user, orderWrapper.getOrderListByStore());
                    return;
                case 2:
                    continue;
            }
        }

    }

    private void purchase(Login user, Map<StoreDto, List<OrderProductDto>> orderListByStore) {
         List<OrderDto> orderList = new InputOrderMemoView().run(user, orderListByStore);
         orderController.insertOrder(orderList);


         /// 주문 출력 -> 문제 : 시간이 등록이 안되어있음
    }

    private void addToCart(Login user, Map<StoreDto, List<OrderProductDto>> orderListByStore) {
        orderController.insertCartList(user, orderListByStore);
    }


    private int inputOrderQuantity(StoreProductDto product) {
        ShopProductPrinter.printProductDetail(product);

        System.out.println("[ 구매할 상품의 수량을 입력해주세요. ]");
        System.out.print(">> 입력:");

        return Integer.parseInt(sc.nextLine());
    }

    private StoreProductDto inputOrderProduct(StoreDto store, List<StoreProductDto> storeProductList) {
        orderController.selectStoreAllProductByStoreId(store);

        ShopProductPrinter.printProductList(store, storeProductList);
        System.out.println("[ 구매할 상품의 번호를 선택해주세요. ]");
        System.out.print(">> 입력:");

        return storeProductList.get(Integer.parseInt(sc.nextLine()) - 1);
    }


    private StoreDto inputStore() {
        List<StoreDto> storeList = orderController.selectStoreList();

        StoreListPrinter.print(storeList);
        System.out.println("[ 상품을 구입할 가맹점을 선택해주세요. ]");
        System.out.print(">> 입력:");

        return storeList.get(Integer.parseInt(sc.nextLine()) - 1);
    }

    private int inputOrderFunction() {
        System.out.print("""
                1. 담은 상품 구매하기
                2. 구매 목록 상품 담기
                0. 장바구니에 담고 구매 종료하기
                >> 입력:""");

        return Integer.parseInt(sc.nextLine());
    }

}
