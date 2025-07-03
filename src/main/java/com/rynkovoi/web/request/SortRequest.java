package com.rynkovoi.web.request;

import com.rynkovoi.type.OrderType;
import com.rynkovoi.type.SortType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SortRequest {
    SortType sortType;
    OrderType orderType;
}
