package com.minisec.user.model.dto.order;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDetailFilterDto {

    private Integer orderId;
    private Integer userId;
    private String orderStatus;

}
