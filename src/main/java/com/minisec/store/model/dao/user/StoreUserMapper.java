package com.minisec.store.model.dao.user;

import com.minisec.store.model.dto.user.StoreUserSelectDto;
import java.util.List;
import java.util.Map;

public interface StoreUserMapper {
    List<StoreUserSelectDto> selectStoreUserStock(int storeId);
    
    //고객 주문ID 가져오기
    int getUserOrderIdByDetailId(int userOrderDetailId);
    
    //고객주문상태 업데이트
    int updateUserOrderStatus(Map<String, Object> params);

    //가맹점 상품 수량 업데이트
    int updateStoreDetailQuantity(int userOrderId);
}
