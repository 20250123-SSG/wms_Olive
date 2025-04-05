package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.OrderController;
import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;
import com.minisec.user.model.helper.OrderWrapper;
import com.minisec.user.view.details.InputOrderMemoView;
import com.minisec.user.view.printer.store.ShopProductPrinter;
import com.minisec.user.view.printer.store.StoreListPrinter;

import java.util.*;

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

            OrderProductDto orderProduct = orderWrapper.addOrder(store, product, quantity);
            orderProduct.getProduct().deleteLocalStoreProductQuantity(quantity);

            System.out.print("""
                1. 담은 상품 구매하기
                2. 구매 목록 상품 담기
                0. 장바구니에 담고 구매 종료하기
                >> 입력:""");

            switch (Integer.parseInt(sc.nextLine())) {
                case 0: addToCart(user, orderWrapper.getOrderListByStore()); return;
                case 1: purchase(user, orderWrapper.getOrderListByStore());  return;
                case 2: continue;
            }
        }
    }


    private void purchase(Login user, Map<StoreDto, List<OrderProductDto>> orderListByStore) {
        List<OrderDto> orderList = new InputOrderMemoView().run(user, orderListByStore);
        orderController.insertOrder(orderList);
    }

    private void addToCart(Login user, Map<StoreDto, List<OrderProductDto>> orderListByStore) {
        orderController.insertCartList(user, orderListByStore);
    }


    private StoreDto inputStore() {
        List<StoreDto> storeList = orderController.selectStoreList();
        StoreListPrinter.print(storeList);
        System.out.println("[ 상품을 구입할 가맹점을 선택해주세요. ]");
        System.out.print(">> 입력:");

        return storeList.get(Integer.parseInt(sc.nextLine()) - 1);
    }

    private StoreProductDto inputOrderProduct(StoreDto store, List<StoreProductDto> storeProductList) {
        orderController.selectStoreAllProductByStoreId(store);
        ShopProductPrinter.printProductList(store, storeProductList);
        System.out.println("[ 구매할 상품의 번호를 선택해주세요. ]");
        System.out.print(">> 입력:");

        return storeProductList.get(Integer.parseInt(sc.nextLine()) - 1);
    }

    private int inputOrderQuantity(StoreProductDto product) {
        ShopProductPrinter.printProductDetail(product);
        System.out.println("[ 구매할 상품의 수량을 입력해주세요. ]");
        System.out.print(">> 입력:");

        return Integer.parseInt(sc.nextLine());
    }

}
