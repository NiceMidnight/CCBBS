package cc.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @FileName SearchData
 * @Description 查询分页信息
 * @Author Tiamo_Null
 * @date 2023-08-08
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchData<T> {
    /**
     * 页数
     */
    private Integer pageNum;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Integer total;
    /**
     * 数据类型
     * 搜索信息
     */
    private T data;

    public static <T> SearchData<T> pageData(Integer pageNum, Integer pageSize, Integer total, List<T> t) {
        return new SearchData(pageNum,pageSize,total,t);
    }
}
