package com.minisec.user.model.dao.store;

import com.minisec.user.model.dto.store.StoreDto;

import java.util.List;

public interface StoreDao {

    List<StoreDto> selectStoreList();

}
