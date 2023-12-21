package cc.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @FileName Dict
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-21
 **/
@Data
@TableName("dict")
public class Dict {
    /**
     * 字典id
     */
    @TableId(value = "dict_id",type = IdType.AUTO)
    private Integer dictId;
    /**
     * 字典类型id
     */
    private Integer dictTypeId;
    /**
     * 字典类型名称
     */
    private String dictTypeName;
    /**
     * 字典主题名详细内容
     */
    private String dictItemName;
}
