package cc.backend.manager.controller;


import cc.backend.commom.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName MenusController
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-04
 **/
@RestController
@RequestMapping("/manager/menu")
public class MenusController {

    @RequestMapping("/getAllMenu")
    public Result getAllMenu(){

        return Result.error("错误");
    }

}
