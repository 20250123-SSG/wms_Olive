package com.minisec.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.minisec.store.dto.StoreProductDto;
import com.minisec.store.service.StoreSelectService;
import com.minisec.store.view.StoreDetailView;

public class StoreSelectController {

    private StoreSelectService storeSelectService = new StoreSelectService();

    private StoreDetailView storeDetailView = new StoreDetailView();

    public void selectProductListAll(int manageId) {
        Map<String, Object> param = new HashMap<>();
        param.put("manageId", manageId);
        List<StoreProductDto> list = storeSelectService.selectAllProduct(param);
        storeDetailView.displayDetailList(list);
    }

    public void selectProductListName(int manageId, String searchName) {
        Map<String, Object> param = new HashMap<>();
        param.put("manageId", manageId);
        param.put("searchName", searchName);
        List<StoreProductDto> list = storeSelectService.selectSearchNameList(param);
        storeDetailView.displayDetailList(list);

    }


    public void selectProductListCategory(int manageId, String searchCategory) {
        Map<String, Object> param = new HashMap<>();
        param.put("manageId", manageId);
        param.put("searchCategory", searchCategory);
        List<StoreProductDto> list = storeSelectService.selectSearchCategoryList(param);
        storeDetailView.displayDetailList(list);
    }

    public void selectProductListLow(int manageId) {
        Map<String, Object> param = new HashMap<>();
        param.put("manageId", manageId);
        List<StoreProductDto> list = storeSelectService.selectSearchLowList(param);
        storeDetailView.displayDetailList(list);
    }
}
