package com.minisec.store.model.dao.order;

import com.minisec.store.model.dto.order.*;

import java.util.List;

public interface StoreOrderMapper {

   //발주등록
   int insertOrderStock(StoreOrderCommonDto storeOrderCommonDto);

   //발주상세등록시 주문아이디가져오기
   int getLastInsertId();

   //발주상세등록
   int insertOrderDetailStock(StoreOrderCommonDetailDto storeOrderDetailDto);
   
   //발주조회
   List<StoreOrderSelectDto> selectOrderStock();

   //발주업데이트
   int updateOrderStock(StoreOrderCommonDto storeOrderDto);

   //발주상세업데이트
   int updateOrderDetailStock(StoreOrderCommonDetailDto storeOrderDetailDto);

   //발주 상품등록용 상품조회
   List<StoreOrderSelectProductByInsertDto> selectStockByOrder();

   //발주 업데이트용 발주현황 조회
   List<StoreOrderSelectByUpdateDto> selectStockByUpdate(int storeId);

   //발주 업데이트시 발주등록상세아이디가져오기
   int getStoreOrderIdByDetailId(int storeOrderDetailId);

}
