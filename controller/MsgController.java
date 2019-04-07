package cn.wsq.controller;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.entity.Msg;
import cn.wsq.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("msg")
public class MsgController {
    @Autowired
    private MsgService msgService;
    /*
    * 按分页查询
    * */
    @RequestMapping("getMsgList")
    public TableResult<Msg> getMsgList(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit){
        TableResult<Msg> msgList = msgService.getMsgList(offset, limit);
        return msgList;
    }
    /*
     * 删除Ids集合
     * */
    @RequestMapping("deleteMsg")
    public Result deleteByIds(String[] ids){
        Result result = msgService.deleteByIds(ids);
        return result;
    }
    /*
     * 保存或者是更新
     * */
    @RequestMapping("saveOrUpdateMsg")
    public Result saveOrUpdateMsg(Msg msg){
        Result result = msgService.saveOrUpdateMsg(msg);
        return result;
    }
    /*
     * 根据Id获取
     * */
    @RequestMapping("getMsgById")
    public Msg getMsgById(String id){
        Msg msg = msgService.getMsgById(id);
        return msg;
    }
    /*
    * 根据实体类对象查询
    * */
    @RequestMapping("searchByEntity")
    public List<Msg> searchByEntity(Msg msg){
        List<Msg> msgs = msgService.searchByEntity(msg);
        return msgs;
    }
    /*
    * 获取所有的数据
    * */
    @RequestMapping("getList")
    public List<Msg> getList(){
        List<Msg> list = msgService.getList();
        return list;
    }
    /*
     * 分页查询和条件查询
     * */
    @RequestMapping("getSearchPage")
    public TableResult<Msg> getSearchPage(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit,Msg msg){
        TableResult<Msg> result=msgService.getSearchPage(offset,limit,msg);
        return result;
    }
}
