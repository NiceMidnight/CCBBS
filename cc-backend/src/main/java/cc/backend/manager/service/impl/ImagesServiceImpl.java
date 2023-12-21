package cc.backend.manager.service.impl;


import cc.backend.commom.Token;
import cc.backend.entity.Dict;
import cc.backend.entity.SearchData;
import cc.backend.entity.SysImageResource;
import cc.backend.enums.SysImgStatus;
import cc.backend.manager.mapper.DictMapper;
import cc.backend.manager.mapper.ImagesMapper;
import cc.backend.manager.service.ImagesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @FileName ImagesServiceImpl
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-21
 **/
@Service
public class ImagesServiceImpl implements ImagesService {
    @Autowired
    private ImagesMapper imagesMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private Token token;
    /**
     * @description TODO 图片资源查询
     */
    @Override
    public SearchData getAllImages(SearchData<SysImageResource> searchData) {
        //  分页查询
        IPage<SysImageResource> iPage = new Page<>(searchData.getPageNum(),searchData.getPageSize());
        imagesMapper.selectAllImg(iPage,searchData.getData().getType(),searchData.getData().getUserName());
        System.out.println(iPage.getRecords());
        return SearchData.pageData((int)iPage.getCurrent(),(int) iPage.getSize(),(int)iPage.getTotal(),iPage.getRecords());
    }

    /**
     * @description TODO 图片可见
     */
    @Override
    public boolean visible(Integer id) {
        return imagesMapper.updateSysImgStatus(id,SysImgStatus.VISIBLE) > 0;
    }
    /**
     * @description TODO 图片不可见
     */
    @Override
    public boolean disVisible(Integer id) {
        return imagesMapper.updateSysImgStatus(id,SysImgStatus.INVISIBLE) > 0;
    }

    /**
     * TODO 获取图片类别选择器内容
     */
    @Override
    public List<Dict> getSysImgOption() {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("dict_type_name","图片类别");
        return dictMapper.selectList(dictQueryWrapper);
    }
    /**
     * TODO 访问地址
     */
    @Value("${addSysImgToDB.path}")
    private String uploadPath;
    /**
     * TODO 添加系统图片信息
     */
    @Override
    public boolean addSysImgData(SysImageResource addSysImgData,String tokenInfo) {
        addSysImgData.setUserId((long) token.getUserId(tokenInfo));
        addSysImgData.setUploadTime(new Date());
        addSysImgData.setImgPath(uploadPath+addSysImgData.getImgPath());
        return imagesMapper.insert(addSysImgData) > 0;
    }
}
