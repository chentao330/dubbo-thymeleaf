package service;

import pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
   //添加用户
    int addUser(User user);
   //修改用户
    int updateUser(User user);
    //查询修改用户的id
    User selectUserById(int id);
    //删除用户
    int deleteUser(int id);
   //查询所有用户
    List<User> selectAllUser();
    //查询用户分页
    List<User> selectUserByPage(Map<String,Integer> pageMap);
    //总数
    int pageCount();

}
