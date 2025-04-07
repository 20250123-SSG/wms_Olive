package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.OrderController;
import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.StoreDto;
import com.minisec.user.model.manager.LocalOrderManager;
import com.minisec.user.view.details.InputOrderMemoView;
import com.minisec.user.view.printer.store.ShopProductPrinter;
import com.minisec.user.view.printer.store.StoreListPrinter;

import java.util.*;

public class OrderView {
    private final Scanner sc = new Scanner(System.in);

    private final OrderController orderController = new OrderController();
    private LocalOrderManager localOrderManager = new LocalOrderManager();


    public void run(Login user) {

        StoreDto store = inputStore();
        if (!readStoreAllProduct(store)) return;

        while (true) {
            StoreProductDto product = inputOrderProduct(store);
            int quantity = inputOrderQuantity(product);

            localOrderManager.addOrder(store, product, quantity);

            System.out.print("""
                    1. 담은 상품 구매하기
                    2. 구매 목록 상품 담기
                    0. 장바구니에 담고 구매 종료하기
                    >> 입력:""");

            switch (Integer.parseInt(sc.nextLine())) {
                case 0: addToCart(user); return;
                case 1: purchase(user);  return;
                case 2: continue;
            }
        }
    }


    private void purchase(Login user) {
        List<OrderDto> orderList = localOrderManager.getOrderList(user);
        orderList = new InputOrderMemoView().run(orderList);

        try {
            orderController.insertOrder(orderList);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addToCart(Login user) {
        orderController.insertCartList(user, localOrderManager.getOrderListByStore());
    }


    private StoreDto inputStore() {
        List<StoreDto> storeList = orderController.selectStoreList();
        StoreListPrinter.print(storeList);
        System.out.println("[ 상품을 구입할 가맹점을 선택해주세요. ]");
        System.out.print(">> 입력:");

        return localOrderManager.getStoreByStoreId(storeList, sc.nextLine());
    }

    private StoreProductDto inputOrderProduct(StoreDto store) {
        orderController.selectStoreAllProductByStoreId(store);
        ShopProductPrinter.printProductList(store, localOrderManager.getStoreProductList());
        System.out.println("[ 구매할 상품의 번호를 선택해주세요. ]");
        System.out.print(">> 입력:");

        return localOrderManager.getOrderProduct(sc.nextLine());
    }

    private int inputOrderQuantity(StoreProductDto product) {
        ShopProductPrinter.printProductDetail(product);
        System.out.println("[ 구매할 상품의 수량을 입력해주세요. ]");
        System.out.print(">> 입력:");

        return localOrderManager.getOrderQuantity(sc.nextLine());
    }

    private boolean readStoreAllProduct(StoreDto store) {
        localOrderManager = new LocalOrderManager(
                orderController.selectStoreAllProductByStoreId(store)
        );
        if (localOrderManager.isEmptyStoreProduct()) {
            System.out.println("해당 가맹점에 판매하는 상품이 존재하지 않습니다.");
            return false;
        }
        return true;
    }

}
