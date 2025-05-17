package com.share.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.share.device.domain.Region;

import java.util.List;

public interface IRegionService extends IService<Region> {

    List<Region> treeSelect(String parentCode);

}