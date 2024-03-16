package cc.backend.manager.service.impl;

import cc.backend.entity.Dict;
import cc.backend.entity.SearchData;
import cc.backend.manager.mapper.DictMapper;
import cc.backend.manager.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName DictServiceImpl
 * @Description 字典
 * @Author Tiamo_Null
 * @date 2023-08-28
 **/
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictMapper dictMapper;
    /**
     * TODO 字典内容
     */
    @Override
    public SearchData<Dict> getAllDict(SearchData<Dict> searchData) {
        IPage<Dict> iPage = new Page<>(searchData.getPageNum(),searchData.getPageSize());
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(searchData.getData().getDictTypeName()!=null, Dict::getDictTypeName,searchData.getData().getDictTypeName());
        dictMapper.selectPage(iPage, queryWrapper);
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(),iPage.getRecords());
    }
    /**
     * TODO 字典类型选择器
     */
    @Override
    public List<Dict> getDictTypeName() {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.select("dict_type_name, MAX(dict_id) as max_dict_id, MAX(dict_type_id) as max_dict_type_id, MAX(dict_item_name) as max_dict_item_name");
        dictQueryWrapper.groupBy("dict_type_name");
        return dictMapper.selectList(dictQueryWrapper);
    }
    /**
     * TODO 添加字典内容
     */
    @Override
    public boolean addDict(Dict dict) {
        return dictMapper.insert(dict) > 0;
    }

    /**
     * TODO 更新字典颜色
     */
    @Override
    public boolean updateDictColor(Integer dictId, String dictColor) {
        return dictMapper.updateDictColor(dictId,dictColor) > 0;
    }

    @Override
    public List<Dict> getUserRole() {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("dict_type_name","role");
        return dictMapper.selectList(dictQueryWrapper);
    }

}
