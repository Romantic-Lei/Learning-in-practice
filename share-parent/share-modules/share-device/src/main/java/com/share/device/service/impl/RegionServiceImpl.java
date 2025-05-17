package com.share.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.device.domain.Region;
import com.share.device.mapper.RegionMapper;
import com.share.device.service.IRegionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {
    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<Region> treeSelect(String parentCode) {
        List<Region> regionList = regionMapper.selectList(new LambdaQueryWrapper<Region>().eq(Region::getParentCode, parentCode));
        if (!CollectionUtils.isEmpty(regionList)) {
            regionList.forEach(item -> {
                long count = regionMapper.selectCount(new LambdaQueryWrapper<Region>().eq(Region::getParentCode, item.getCode()));
                if (count > 0) {
                    item.setHasChildren(true);
                } else {
                    item.setHasChildren(false);
                }
            });
        }
        return regionList;
    }

    @Override
    public String getNameByCode(String code) {
        if (StringUtils.isEmpty(code))
            return "";

        Region region = regionMapper.selectOne(new LambdaQueryWrapper<Region>()
                .eq(Region::getCode, code)
                .select(Region::getName));
        if (null == region)
            return "";
        return region.getName();
    }

}