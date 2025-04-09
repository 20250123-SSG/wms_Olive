package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.OrderController;
import com.minisec.user.model.dto.order.OrderDetailFilterDto;
import com.minisec.user.model.dto.store.StoreProductDto;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.store.StoreDto;
import com.minisec.user.model.manager.LocalOrderManager;
import com.minisec.user.view.details.InputFunctionNumberView;
import com.minisec.user.view.details.InputOrderMemoView;
import com.minisec.user.view.printer.ExceptionPrinter;
import com.minisec.user.view.printer.order.OrderDetailsPrinter;
import com.minisec.user.view.printer.store.ShopProductPrinter;
import com.minisec.user.view.printer.store.StoreListPrinter;

import java.util.*;

public class OrderView {
    private final Scanner sc = new Scanner(System.in);

    private final OrderController orderController = new OrderController();
    private LocalOrderManager localOrderManager = new LocalOrderManager();

    public void run(Login user) {
        System.out.println("\n\n====================================================");
        System.out.println("--------------------- 구매하기 ---------------------\n");

        StoreDto store = inputStore();
        if (!readStoreAllProduct(store)) return;

        while (true) {
            StoreProductDto product = inputOrderProduct(store);
            if (product == null) break;
            int quantity = inputOrderQuantity(product);

            try {
                localOrderManager.addOrder(store, product, quantity);
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
                continue;
            }

            while (true) {
                System.out.print("""
                        \n\n
                        1. 담은 상품 구매하기
                        2. 구매 목록 상품 담기
                        0. 장바구니에 담고 구매 종료하기
                        """);
                int functionNum = InputFunctionNumberView.input();

                if (functionNum == 2) {
                    if (localOrderManager.isEmptyStoreProduct()) {
                        System.out.println("\n\n모든 상품을 담았습니다.");
                        continue;
                    }
                    break;
                }
                if (functionNum == 1) {
                    purchase(user);
                    return;
                }
                if (functionNum == 0) {
                    addToCart(user);
                    return;
                }

                ExceptionPrinter.print("존재하지 않는 기능입니다.");
            }
        }
    }


    private void purchase(Login user) {
        List<OrderDto> orderList = localOrderManager.getOrderList(user);
        orderList = new InputOrderMemoView().run(orderList);

        try {
            orderController.insertOrder(orderList);
        } catch (IllegalArgumentException e) {
            ExceptionPrinter.print(e.getMessage());
        }
    }


    private void addToCart(Login user) {
        orderController.insertCartList(user, localOrderManager.getOrderListByStore());
    }


    private StoreDto inputStore() {
        Map<Integer, StoreDto> storeListByUniqueNumber = orderController.selectStoreListByUniqueNumber();
        StoreListPrinter.print(storeListByUniqueNumber);

        while (true) {
            System.out.println("[ 상품을 구입할 가맹점을 선택해주세요 ]");
            System.out.print(">> 입력:");
            String storeNum = sc.nextLine();

            try {
                return localOrderManager.getStoreByStoreId(storeListByUniqueNumber, storeNum);
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
            }
        }
    }


    private StoreProductDto inputOrderProduct(StoreDto store) {
        orderController.selectStoreAllProductByStoreId(store);
        ShopProductPrinter.printProductListByCategory(store, localOrderManager.getStoreProductByCategoryForPrint());
        while (true) {
            System.out.println("[ 구매할 상품의 번호를 선택해주세요. (0. 뒤로가기) ]");
            System.out.print(">> 입력:");
            String productNum = sc.nextLine();

            if ("0".equals(productNum)) return null;
            try {
                return localOrderManager.getOrderProduct(productNum);
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
            }
        }
    }


    private int inputOrderQuantity(StoreProductDto product) {
        ShopProductPrinter.printProductDetail(product);

        while (true) {
            System.out.println("[ 구매할 상품의 수량을 입력해주세요. ]");
            System.out.print(">> 입력:");

            try {
                int quantity = localOrderManager.getOrderQuantity(sc.nextLine());
                OrderDetailsPrinter.printTotalPriceByOneProduct(product, quantity);
                return quantity;
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
            }

        }
    }


    private boolean readStoreAllProduct(StoreDto store) {
        localOrderManager = new LocalOrderManager(
                orderController.selectStoreAllProductByStoreId(store)
        );
        if (localOrderManager.isEmptyStoreProduct()) {
            System.out.println("\n\n해당 가맹점에 판매하는 상품이 존재하지 않습니다.");
            return false;
        }
        return true;
    }

}
