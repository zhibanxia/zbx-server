package cn.zhibanxia.zbxserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zzy on  2018/10/02 14:48
 */
@RestController("recyle")
public class RecyleCtrl {
    /**
     * 获取回收列表，根据参数不同，展示不同的数据：
     * bizType
     * 1.回收人员查看待回收列表
     * 2.回收人员查询已经确认的列表
     * 3.业主查询自己提交的回收请求
     *
     * @return
     */
    @GetMapping("list")
    public ModelAndView list() {
        return null;
    }
}
