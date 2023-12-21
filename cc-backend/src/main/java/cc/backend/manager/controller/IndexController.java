package cc.backend.manager.controller;

import cc.backend.commom.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/12/8
 */
@RestController
@RequestMapping("/manager/index")
public class IndexController {
    @RequestMapping("/getIndexData")
    public Result getIndexData() {

        return Result.error("获取失败");
    }

}

