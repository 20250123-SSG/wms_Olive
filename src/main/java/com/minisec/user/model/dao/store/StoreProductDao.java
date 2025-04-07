package com.minisec.user.model.dao.store;

import com.minisec.user.model.dto.store.StoreProductDto;
import com.minisec.user.model.dto.store.StoreInventoryDeductionDto;

import java.util.List;

public interface StoreProductDao {

    List<StoreProductDto> selectStoreAllProductByStoreId(int storeId);

    int decreaseStoreProductQuantity(StoreInventoryDeductionDto storeInventoryUpdateDetail);

}
