package cc.backend.manager.service;

import cc.backend.entity.Dict;
import cc.backend.entity.SearchData;

import java.util.List;

/**
 * @FileName DictService
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-28
 **/
public interface DictService {
    SearchData<Dict> getAllDict(SearchData<Dict> searchData);
    List<Dict> getDictTypeName();
    boolean addDict(Dict dict);
}
