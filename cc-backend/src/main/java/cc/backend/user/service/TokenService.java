package cc.backend.user.service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/20
 */
public interface TokenService {
    boolean tokenVerification(String tokenInfo);

}
