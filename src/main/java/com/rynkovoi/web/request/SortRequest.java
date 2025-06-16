package com.rynkovoi.web.request;

import com.rynkovoi.type.SortType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jooq.SortOrder;

@Getter
@Setter
@Builder
@ToString
public class SortRequest {

    SortType sortType;
    SortOrder sortOrder;
}
