package com.minisec.user.model.dao.order;

import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.order.StoreInventoryDeductionDto;

import java.util.List;

public interface StoreProductDao {

    List<StoreProductDto> selectStoreAllProductByStoreId(int storeId);

    int decreaseStoreProductQuantity(StoreInventoryDeductionDto storeInventoryUpdateDetail);
}
