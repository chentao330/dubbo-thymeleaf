package com.syedu.serviceImpl;

import com.syedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.User;
import service.UserService;
import java.util.List;
import java.util.Map;


@Service
@com.alibaba.dubbo.config.annotation.Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
    @Transactional
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User selectUserById(int id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public List<User> selectUserByPage(Map<String, Integer> pageMap) {
        List<User> users =(List<User>) redisTemplate.opsForValue().get("users");
        if (null==users){
            users= userMapper.selectUserByPage(pageMap);
            redisTemplate.opsForValue().set("users",users);
            return users;
        }else{
            return users;
        }

    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int pageCount() {
        return userMapper.pageCount();
    }
}
