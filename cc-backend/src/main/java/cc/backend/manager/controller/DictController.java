package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.Dict;
import cc.backend.entity.SearchData;
import cc.backend.manager.service.impl.DictServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName DictController
 * @Description 字典
 * @Author Tiamo_Null
 * @date 2023-08-28
 **/
@RestController
@RequestMapping("/manager/dict")
public class DictController {
    @Autowired
    private DictServiceImpl dictService;

    /**
     * TODO 查询字典内容
     */
    @PostMapping("/getAllDict")
    public Result getAllDict(@RequestBody SearchData<Dict> searchData) {
        SearchData<Dict> dict = dictService.getAllDict(searchData);
        return Result.successCDM(dict,"获取数据成功");
    }

    /**
     * TODO 获取字典类型名称给选择器
     */
    @GetMapping("/getDictTypeName")
    public Result getDictTypeName() {
        List<Dict> dictTypeName = dictService.getDictTypeName();
        if (dictTypeName != null) {
            return Result.successCDM(dictTypeName,"获取字典类型名称成功");
        }
        return Result.error("获取失败");
    }

    /**
     * TODO 添加字典内容
     */
    @PostMapping("/addDict")
    public Result addDict(@RequestBody Dict dict) {
        System.out.println(dict);
        boolean isAddDict = dictService.addDict(dict);
        System.out.println(isAddDict);
        if (isAddDict) {
            return Result.successCM("添加字典内容成功");
        }
        return Result.error("添加字典内容失败");
    }


    @GetMapping("/updateDictColor")
    public Result updateDictColor(@RequestParam("dictId")Integer dictId,@RequestParam("dictColor")String dictColor)
    {
        boolean isUpdateDictColor = dictService.updateDictColor(dictId, dictColor);
        if (isUpdateDictColor)
        {
            return Result.successCM("更改字典颜色成功");
        }
        return Result.error("更改字典颜色失败");
    }

    @GetMapping("/getUserRole")
    public Result getUserRole()
    {
        List<Dict> userRole = dictService.getUserRole();
        return Result.successCDM(userRole,"获取用户角色字典成功");
    }

}
