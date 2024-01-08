package cc.backend.entity;

import cc.backend.enums.UserStatus;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @FileName User
 * @Description User实体类
 * @Author Tiamo_Null
 * @date 2023-07-28
 **/
@TableName("user")
@Data
@AllArgsConstructor //自动构造有参方法
@NoArgsConstructor  //自动构造无参方法
public class User {
    /**
     * 用户id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 头像
     */
    private String userHead;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 密码不被查询
     */
    @TableField(select = false)
    private String password;
    /**
     * 性别
     */
    private Integer userSex;
    /**
     * 年龄
     */
    private Integer userAge;
    /**
     * 电话
     */
    private String userPhone;
    /**
     * 住址
     */
    private String userAddress;
    /**
     * 创建日期
     */
    private Date userDate;
    /**
     * 角色  管理员/用户
     */
    private Integer userRole;
    /**
     * 状态
     */
    private UserStatus userStatus;
    /**
     * 逻辑删除字段
     */
//      @TableLogic(value = "1",delval = "0")
    private Integer userDeleted;
    /**
     * 乐观锁
     */
    @Version
    private Integer userVersion;
    /**
     * 个性签名
     */
    private String userPersonality;
    /**
     * 粉丝
     */
    private int fans;
}
