package com.minisec.store.controller;

import com.minisec.store.dto.StoreProductDto;
import com.minisec.store.service.StoreSelectService;
import com.minisec.store.view.StoreDetailView;

import java.util.List;

public class StoreSelectController {

    private StoreSelectService storeSelectService = new StoreSelectService();

    public void selectProductListAll(int manageId) {
        List<StoreProductDto> list = storeSelectService.selectProductListAll(manageId);
        StoreDetailView.displayDetailList(list);
    }

    public void selectProductListName(int manageId, String searchName) {
        List<StoreProductDto> list = storeSelectService.selectSearchNameList(manageId, searchName);
        com.minisec.store.view.StoreDetailView.displayDetailList(list);
    }


    public void selectProductListCategory(int manageId, String searchCategory) {
        List<StoreProductDto> list = storeSelectService.selectSearchCategoryList(manageId, searchCategory);
        com.minisec.store.view.StoreDetailView.displayDetailList(list);
    }

    public void selectProductListLow(int manageId) {
        List<StoreProductDto> list = storeSelectService.selectSearchLowList(manageId);
        com.minisec.store.view.StoreDetailView.displayDetailList(list);
    }
}
