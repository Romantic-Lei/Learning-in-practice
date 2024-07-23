package com.luojia.demo.selfRedisCache.service.impl;

import com.luojia.demo.entity.User;
import com.luojia.demo.mapper.UserMapper;
import com.luojia.demo.selfRedisCache.annotation.LuoJiaRedisCache;
import com.luojia.demo.selfRedisCache.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    //==========第一组，only mysql==========
    /*@Resource
    private UserMapper userMapper;

    @Override
    public int addUser(User user)
    {
        return userMapper.insertSelective(user);
    }

    @Override
    public User getUserById(Integer id)
    {
        return userMapper.selectByPrimaryKey(id);
    }*/


    //==========第二组，redis+mysql==========
    public static final String CACHE_KEY_USER = "user:";
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public int addUser(User user) {
        log.info("插入之前user:{}",user);
        int retValue = userMapper.insertSelective(user);
        log.info("插入之后user:{}",user);
        log.info("=================================");

        if(retValue > 0) {
            //到数据库里面，重新捞出新数据出来，做缓存
            user=this.userMapper.selectByPrimaryKey(user.getId());
            //缓存key
            String key=CACHE_KEY_USER+user.getId();
            //往mysql里面插入成功随后再从mysql查询出来，再插入redis
            redisTemplate.opsForValue().set(key,user);
        }
        return retValue;
    }


    @Override
    public User getUserById(Integer id) {
        User user = null;

        //缓存key
        String key=CACHE_KEY_USER+id;
        //1 查询redis
        user = (User) redisTemplate.opsForValue().get(key);

        //redis无，进一步查询mysql
        if(user==null) {
            //从mysql查出来user
            user=this.userMapper.selectByPrimaryKey(id);
            // mysql有，redis无
            if (user != null) {
                //把mysql捞到的数据写入redis，方便下次查询能redis命中。
                redisTemplate.opsForValue().set(key,user);
            }
        }
        return user;
    }

    @Override
    public int addUserAop(User user) {
        log.info("插入之前user：{}", user);
        int retValue = userMapper.insertSelective(user);
        log.info("插入之后user:{}", user);
        if (retValue > 0) {
            user = userMapper.selectByPrimaryKey(user.getId());
            String key = CACHE_KEY_USER + user.getId();
            redisTemplate.opsForValue().set(key, user);
        }
        return retValue;
    }

    /**
     * 会将返回值存进redis里，key生成规则需要程序员用SpEL表达式自己指定，value就是程序从mysql查出并返回的user
     * redis的key 等于  keyPrefix:matchValue
     */
    @Override
    @LuoJiaRedisCache(keyPrefix = "user", matchValue = "#id")
    public User getUserByIdAop(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}