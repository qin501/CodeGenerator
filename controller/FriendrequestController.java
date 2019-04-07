package cn.wsq.controller;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.entity.Friendrequest;
import cn.wsq.service.FriendrequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("friendrequest")
public class FriendrequestController {
    @Autowired
    private FriendrequestService friendrequestService;
    /*
    * 按分页查询
    * */
    @RequestMapping("getFriendrequestList")
    public TableResult<Friendrequest> getFriendrequestList(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit){
        TableResult<Friendrequest> friendrequestList = friendrequestService.getFriendrequestList(offset, limit);
        return friendrequestList;
    }
    /*
     * 删除Ids集合
     * */
    @RequestMapping("deleteFriendrequest")
    public Result deleteByIds(String[] ids){
        Result result = friendrequestService.deleteByIds(ids);
        return result;
    }
    /*
     * 保存或者是更新
     * */
    @RequestMapping("saveOrUpdateFriendrequest")
    public Result saveOrUpdateFriendrequest(Friendrequest friendrequest){
        Result result = friendrequestService.saveOrUpdateFriendrequest(friendrequest);
        return result;
    }
    /*
     * 根据Id获取
     * */
    @RequestMapping("getFriendrequestById")
    public Friendrequest getFriendrequestById(String id){
        Friendrequest friendrequest = friendrequestService.getFriendrequestById(id);
        return friendrequest;
    }
    /*
    * 根据实体类对象查询
    * */
    @RequestMapping("searchByEntity")
    public List<Friendrequest> searchByEntity(Friendrequest friendrequest){
        List<Friendrequest> friendrequests = friendrequestService.searchByEntity(friendrequest);
        return friendrequests;
    }
    /*
    * 获取所有的数据
    * */
    @RequestMapping("getList")
    public List<Friendrequest> getList(){
        List<Friendrequest> list = friendrequestService.getList();
        return list;
    }
    /*
     * 分页查询和条件查询
     * */
    @RequestMapping("getSearchPage")
    public TableResult<Friendrequest> getSearchPage(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit,Friendrequest friendrequest){
        TableResult<Friendrequest> result=friendrequestService.getSearchPage(offset,limit,friendrequest);
        return result;
    }
}
