package com.share.device.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.share.common.core.exception.ServiceException;
import com.share.device.service.IMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class MapServiceImpl implements IMapService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Double calculateDistance(String startLongitude,String startLatitude,String endLongitude,String endLatitude) {
        String url = "https://apis.map.qq.com/ws/direction/v1/walking/?from={from}&to={to}&key={key}";

        Map<String, String> map = new HashMap<>();
        map.put("from", startLatitude + "," + startLongitude);
        map.put("to", endLatitude + "," + endLongitude);
        map.put("key", getKey());

        JSONObject result = restTemplate.getForObject(url, JSONObject.class, map);
        if(result.getIntValue("status") != 0) {
            throw new ServiceException("地图服务调用失败");
        }

        //返回第一条最佳线路
        JSONObject route = result.getJSONObject("result").getJSONArray("routes").getJSONObject(0);
        // 单位：米
        return route.getBigDecimal("distance").doubleValue();
    }

    public String getKey() {
        String filePath = "D:\\file\\key.txt";
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream.findFirst().orElse("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
