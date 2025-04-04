package com.minisec.store.controller;

import com.minisec.store.dto.StoreProductDto;
import com.minisec.store.service.StoreModifyService;
import com.minisec.store.view.StoreDetailView;

import java.util.List;
import java.util.Map;

public class StoreModifyController {
    private StoreModifyService storeModifyService = new StoreModifyService();

    public void productList() {
        List<StoreProductDto> list = storeModifyService.ProductList();
        StoreDetailView.displayProductList(list);
    }

    public void insertStoreProduct(Map<String, String> requestParam, int manageId) {
        StoreProductDto storeProduct = StoreProductDto.builder()
                .productId(Integer.parseInt(requestParam.get("productId")))
                .storeDetailPrice(Integer.parseInt(requestParam.get("storeDetailPrice")))
                .storeId(manageId)
                .build();

        int result = StoreModifyService.insertStoreProduct(storeProduct);
        StoreDetailView.displayModifyResult("", result);

    }

    public void selectStoreProductById(String productId){
        List<StoreProductDto> list = storeModifyService.selectStoreProductById(productId);
        StoreDetailView.displayProductList(list);
    }


}
