package com.share.device.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Date;

@Data
@Schema(description = "站点位置")
public class StationLocation {

    @Schema(description = "id")
    @Id
    private String id;

    @Schema(description = "柜机id")
    private Long stationId;

    @Schema(description = "经纬度")
    private GeoJsonPoint location;

    @Schema(description = "创建时间")
    private Date createTime;
}