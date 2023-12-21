package cc.backend.commom;

import lombok.Data;

/**
 * @FileName Result
 * @Description Controller统一返回的包装类（json格式）
 * @Author Tiamo_Null
 * @date 2023-07-29
 **/
@Data
public class Result {
    public static final String SUCCESS_CODE = "200";
    public static final String ERROR_CODE = "500";
    private String code;    //返回的状态码，告诉前端请求成功/失败
    private String msg;     //错误信息
    private Object data;    //包装的数据
    private String token;   //token令牌



    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //登录成功且需要发送数据，刷新页面保持登录状态
    public Result(String code,String token, Object data) {
        this.code = code;
        this.token = token;
        this.data = data;
    }
    public static Result successCDT(Object data,String token){
        return new Result(SUCCESS_CODE,token,data);
    }
    //登录成功且需要发送数据，刷新页面保持登录状态

    // 登录失败
    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static Result error(String msg){
        return new Result(ERROR_CODE,msg);
    }

    //登录成功无需发送数据，刷新页面保持登录状态
    public Result(String code, String token, String msg) {
        this.code = code;
        this.token = token;
        this.msg = msg;
    }
    public static Result successCTM(String token,String msg){
        return new Result(SUCCESS_CODE,token,msg);
    }
    public Result(String code,Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static Result successCDM(Object data, String msg) {
        return new Result(SUCCESS_CODE,data,msg);
    }

    public static Result successCM(String msg) {
        return new Result(SUCCESS_CODE,msg);
    }
}
