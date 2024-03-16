package cc.backend.manager.mapper;


import cc.backend.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @FileName DictMapper
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-21
 **/
public interface DictMapper extends BaseMapper<Dict> {
    @Update("UPDATE dict " +
            "SET dict_color = #{dictColor} " +
            "WHERE dict_id = #{dictId}")
    int updateDictColor(@Param("dictId")Integer dictId,@Param("dictColor")String dictColor);
}
