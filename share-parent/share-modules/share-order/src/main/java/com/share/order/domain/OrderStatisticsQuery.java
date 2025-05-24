package com.share.order.domain;

import lombok.Data;

@Data
public class OrderStatisticsQuery {


	private String orderDateBegin;
	private String orderDateEnd;
    private String provinceCode;
    private String cityCode;
    private String districtCode;

}

