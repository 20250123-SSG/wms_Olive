package com.minisec.user.model.dao.order;

import com.minisec.user.model.dto.order.StoreDto;

import java.util.List;

public interface StoreDao {

    List<StoreDto> selectStoreList();

}
