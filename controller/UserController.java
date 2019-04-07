package cn.wsq.controller;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.entity.User;
import cn.wsq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    /*
    * 按分页查询
    * */
    @RequestMapping("getUserList")
    public TableResult<User> getUserList(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit){
        TableResult<User> userList = userService.getUserList(offset, limit);
        return userList;
    }
    /*
     * 删除Ids集合
     * */
    @RequestMapping("deleteUser")
    public Result deleteByIds(String[] ids){
        Result result = userService.deleteByIds(ids);
        return result;
    }
    /*
     * 保存或者是更新
     * */
    @RequestMapping("saveOrUpdateUser")
    public Result saveOrUpdateUser(User user){
        Result result = userService.saveOrUpdateUser(user);
        return result;
    }
    /*
     * 根据Id获取
     * */
    @RequestMapping("getUserById")
    public User getUserById(String id){
        User user = userService.getUserById(id);
        return user;
    }
    /*
    * 根据实体类对象查询
    * */
    @RequestMapping("searchByEntity")
    public List<User> searchByEntity(User user){
        List<User> users = userService.searchByEntity(user);
        return users;
    }
    /*
    * 获取所有的数据
    * */
    @RequestMapping("getList")
    public List<User> getList(){
        List<User> list = userService.getList();
        return list;
    }
    /*
     * 分页查询和条件查询
     * */
    @RequestMapping("getSearchPage")
    public TableResult<User> getSearchPage(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit,User user){
        TableResult<User> result=userService.getSearchPage(offset,limit,user);
        return result;
    }
}
