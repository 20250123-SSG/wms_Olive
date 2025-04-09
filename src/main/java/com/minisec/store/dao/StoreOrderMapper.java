package com.minisec.store.dao;

import com.minisec.store.dto.StoreOrderDetailDto;
import com.minisec.store.dto.StoreOrderDto;
import com.minisec.store.dto.StoreOrderProduct;
import com.minisec.store.dto.StoreOrderSelectDto;

import java.util.List;

public interface StoreOrderMapper {

   //발주등록
   int insertOrderStock(StoreOrderDto storeOrderDto);

   //발주상세등록시 주문아이디가져오기
   int getLastInsertId();

   //발주상세등록
   int insertOrderDetailStock(StoreOrderDetailDto storeOrderDetailDto);
   
   //발주목록 조회
   List<StoreOrderSelectDto> selectOrderStock(int storeId);

   //발주업데이트
   int updateOrderStock(StoreOrderDto storeOrderDto);

   //발주상세업데이트
   int updateOrderDetailStock(StoreOrderDetailDto storeOrderDetailDto);

   //발주 상품등록용
   List<StoreOrderProduct> selectStockByOrder();

}
