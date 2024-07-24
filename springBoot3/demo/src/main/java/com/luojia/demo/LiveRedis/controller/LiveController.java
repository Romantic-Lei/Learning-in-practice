package com.luojia.demo.LiveRedis.controller;

import com.luojia.demo.LiveRedis.entity.Constants;
import com.luojia.demo.LiveRedis.entity.Content;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class LiveController {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 某个用户(userId=12)第一次进入到房间，返回最新的前5条弹幕
     * @param roomId
     * @param userId
     * @return
     */
    @GetMapping(value = "/goRoom")
    public List<Content> getRoom(Integer roomId, Integer userId) {
        List<Content> list = new ArrayList<>();
        String key = Constants.ROOM_KEY+roomId;
        // 刚进入房间，获取最新的前5条弹幕
        Set<ZSetOperations.TypedTuple<Content>> rang = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 4);
        for (ZSetOperations.TypedTuple<Content> obj : rang) {
            list.add(obj.getValue());
            log.info("首次进房间取最新前5条弹幕contet={},score={}",
                    obj.getValue(), obj.getScore().longValue());
        }

        String userKey = Constants.ROOM_USER_TIME_KEY + userId;
        // 把当前的时间T，保存到Redis，供下次拉取用，看过的数据就不再推送
        Long now = System.currentTimeMillis() / 1000;
        redisTemplate.opsForValue().set(userKey, now);
        return list;
    }

    /**
     * 登录房间后持续观看，定时任务 客户端间隔5秒来拉取数据
     * @param roomId
     * @param userId
     * @return
     */
    @GetMapping(value = "/commentList")
    public List<Content> commentList(Integer roomId, Integer userId) {
        List<Content> list = new ArrayList<>();
        String key = Constants.ROOM_KEY+roomId;
        String userKey = Constants.ROOM_USER_TIME_KEY + userId;
        Long now = System.currentTimeMillis() / 1000;

        // 拿取上次的读取时间
        Long ago = Long.parseLong(redisTemplate.opsForValue().get(userKey).toString());
        log.info("查找时间范围：{}， {}", ago, now);
        Set<ZSetOperations.TypedTuple<Content>> rang = redisTemplate.opsForZSet().rangeByScoreWithScores(key, ago, now);
        for (ZSetOperations.TypedTuple<Content> obj : rang) {
            list.add(obj.getValue());
            log.info("持续观看直播 contet={},score={}",
                    obj.getValue(), obj.getScore().longValue());
        }
        // 当前时间Time保存到Redis，供下次使用
        redisTemplate.opsForValue().set(userKey, now);

        return list;
    }
}
