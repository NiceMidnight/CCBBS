package cc.backend.user.mapper;

import cc.backend.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/17
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
    IPage<Post> selectAllPost(IPage<Post> iPage, @Param("postMsg") String postMsg);
}
