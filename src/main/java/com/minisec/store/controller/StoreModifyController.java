package com.minisec.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.minisec.store.dto.StoreProductDto;
import com.minisec.store.service.StoreModifyService;
import com.minisec.store.view.StoreDetailView;

public class StoreModifyController {
    private StoreModifyService storeModifyService = new StoreModifyService();
    private StoreDetailView storeDetailView = new StoreDetailView();

    public void productList(int storeId) {
        List<StoreProductDto> list = storeModifyService.ProductList(storeId);

        StoreDetailView.displayProductList(list);
    }

    public void insertStoreProduct(Map<String, String> requestParam, int manageId) {
        Map<String, Object> param = new HashMap<>();
        param.put("storeId", manageId);
        param.put("productId", Integer.parseInt(requestParam.get("productId")));
        param.put("storeDetailPrice", Integer.parseInt(requestParam.get("storeDetailPrice")));

        int result = storeModifyService.insertStoreProduct(param);
        storeDetailView.displayModifyResult("\n상품 등록", result);

    }

    public void selectStoreProductById(String productId, int manageId) {
        Map<String, String> param = new HashMap<>();
        param.put("productId", productId);
        param.put("manageId", String.valueOf(manageId));
        List<StoreProductDto> list = storeModifyService.selectStoreProductById(param);
        storeDetailView.displayProductList(list);
    }

    public void updateStoreProduct(Map<String, String> requestParam, int manageId) {
        Map<String, Object> param = new HashMap<>();
        param.put("productId", Integer.parseInt(requestParam.get("productId")));
        param.put("storeDetailPrice", Integer.parseInt(requestParam.get("storeDetailPrice")));
        param.put("isDiscount", Integer.parseInt(requestParam.get("isDiscount")));
        param.put("storeDetailStatus", Integer.parseInt(requestParam.get("storeDetailStatus")));
        param.put("manageId", manageId);

        int result = storeModifyService.updateStoreProduct(param);
        storeDetailView.displayModifyResult("\n상품 수정", result);
    }

    public void deleteStoreProduct(Map<String, String> requestParam, int manageId) {
        Map<String, Object> param = new HashMap<>();
        param.put("productId", Integer.parseInt(requestParam.get("productId")));
        param.put("manageId", manageId);
        int result = storeModifyService.deleteStoreProduct(param);
        storeDetailView.displayModifyResult("\n상품 삭제", result);

    }

}
