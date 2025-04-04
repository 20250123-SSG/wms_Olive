package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.StorageDto;

import java.util.List;

public interface StorageMapper {
    List<StorageDto> selectAllStorage();
}
