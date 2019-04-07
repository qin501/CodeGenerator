package cn.wsq.controller;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.entity.Friends;
import cn.wsq.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("friends")
public class FriendsController {
    @Autowired
    private FriendsService friendsService;
    /*
    * 按分页查询
    * */
    @RequestMapping("getFriendsList")
    public TableResult<Friends> getFriendsList(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit){
        TableResult<Friends> friendsList = friendsService.getFriendsList(offset, limit);
        return friendsList;
    }
    /*
     * 删除Ids集合
     * */
    @RequestMapping("deleteFriends")
    public Result deleteByIds(String[] ids){
        Result result = friendsService.deleteByIds(ids);
        return result;
    }
    /*
     * 保存或者是更新
     * */
    @RequestMapping("saveOrUpdateFriends")
    public Result saveOrUpdateFriends(Friends friends){
        Result result = friendsService.saveOrUpdateFriends(friends);
        return result;
    }
    /*
     * 根据Id获取
     * */
    @RequestMapping("getFriendsById")
    public Friends getFriendsById(String id){
        Friends friends = friendsService.getFriendsById(id);
        return friends;
    }
    /*
    * 根据实体类对象查询
    * */
    @RequestMapping("searchByEntity")
    public List<Friends> searchByEntity(Friends friends){
        List<Friends> friendss = friendsService.searchByEntity(friends);
        return friendss;
    }
    /*
    * 获取所有的数据
    * */
    @RequestMapping("getList")
    public List<Friends> getList(){
        List<Friends> list = friendsService.getList();
        return list;
    }
    /*
     * 分页查询和条件查询
     * */
    @RequestMapping("getSearchPage")
    public TableResult<Friends> getSearchPage(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit,Friends friends){
        TableResult<Friends> result=friendsService.getSearchPage(offset,limit,friends);
        return result;
    }
}
