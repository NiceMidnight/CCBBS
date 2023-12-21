package cc.backend.user.entity;


import cc.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName LoginRequest
 * @Description 未用的验证码方法
 * @Author Tiamo_Null
 * @date 2023-08-16
 **/
@Data
@AllArgsConstructor //自动构造有参方法
@NoArgsConstructor  //自动构造无参方法
public class LoginRequest {
    private User user;
    private String code;

}
