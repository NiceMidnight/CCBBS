package cc.backend.manager.service.impl;


import cc.backend.common.token.Token;
import cc.backend.entity.Dict;
import cc.backend.entity.SearchData;
import cc.backend.entity.SysImageResource;
import cc.backend.enums.SysImgStatus;
import cc.backend.manager.mapper.DictMapper;
import cc.backend.manager.mapper.ImagesMapper;
import cc.backend.manager.service.ImagesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

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
     * @param searchData
     * @return: cc.backend.entity.SearchData<cc.backend.entity.SysImageResource>
     */
    @Override
    public SearchData<SysImageResource> getAllImages(SearchData<SysImageResource> searchData) {
        //  分页查询
        IPage<SysImageResource> iPage = new Page<>(searchData.getPageNum(),searchData.getPageSize());
        imagesMapper.selectAllImg(iPage,searchData.getData().getType(),searchData.getData().getUserName());
        System.out.println(iPage.getRecords());
        return SearchData.pageData((int)iPage.getCurrent(),(int) iPage.getSize(),(int)iPage.getTotal(),iPage.getRecords());
    }

    /**
     * @description TODO 图片可见
     * @param id
     * @return: boolean
     */
    @Override
    public boolean visible(Integer id) {
        return imagesMapper.updateSysImgStatus(id,SysImgStatus.VISIBLE) > 0;
    }

    /**
     * @description TODO 图片不可见
     * @param id
     * @return: boolean
     */
    @Override
    public boolean disVisible(Integer id) {
        return imagesMapper.updateSysImgStatus(id,SysImgStatus.INVISIBLE) > 0;
    }

    /**
     * @description TODO 获取图片类别选择器内容
     * @return: java.util.List<cc.backend.entity.Dict>
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
     * @description TODO 添加系统图片信息
     * @param addSysImgData
     * @param tokenInfo
     * @return: boolean
     */
    @Override
    public boolean addSysImgData(SysImageResource addSysImgData,String tokenInfo) {
        addSysImgData.setUserId((long) token.getUserId(tokenInfo));
        addSysImgData.setUploadTime(new Date());
        addSysImgData.setImgPath(uploadPath+addSysImgData.getImgPath());
        return imagesMapper.insert(addSysImgData) > 0;
    }

    /**
     * @description TODO 通过id获取图片数据
     * @param imgId
     * @return: cc.backend.entity.SysImageResource
     */
    @Override
    public SysImageResource getSysImgResourceById(Integer imgId) {
        return imagesMapper.selectById(imgId);
    }

    /**
     * @description TODO 删除系统图片
     * @param imgId
     * @return: java.lang.String
     */
    @Override
    public boolean deleteSysImgResourceById(Integer imgId) {
        return imagesMapper.deleteById(imgId) > 0;
    }

    /**
     * @description TODO 更新图片数据
     * @param sysImageResource
     * @return: boolean
     */
    @Override
    public boolean updateSysImageResource(SysImageResource sysImageResource) {

        return imagesMapper.updateSysImageResource(sysImageResource) > 0;
    }
}
