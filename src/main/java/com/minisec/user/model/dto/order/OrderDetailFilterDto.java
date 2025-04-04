package com.minisec.user.model.dto.order;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDetailFilterDto {

    private Integer orderId; //현재 구매한것만 출력할떼
    private Integer userId; //사용자 주문내역 출력학ㅎㄱ댸
    private String orderStatus; //취소조회

}
