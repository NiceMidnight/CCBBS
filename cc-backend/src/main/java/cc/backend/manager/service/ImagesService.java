package cc.backend.manager.service;


import cc.backend.entity.Dict;
import cc.backend.entity.SearchData;
import cc.backend.entity.SysImageResource;

import java.util.List;

/**
 * @FileName ImagesService
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-21
 **/
public interface ImagesService {
    SearchData getAllImages(SearchData<SysImageResource> searchData);
    boolean visible(Integer id);
    boolean disVisible(Integer id);
    List<Dict> getSysImgOption();
    boolean addSysImgData(SysImageResource addSysImgData,String tokenInfo);
}
