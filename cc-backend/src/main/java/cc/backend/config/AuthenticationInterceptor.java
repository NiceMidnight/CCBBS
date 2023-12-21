package cc.backend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/7
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在这里获取前端发送的Token
        String authorizationHeader = request.getHeader("Authorization");
        if (isValidToken(authorizationHeader)) {
            // Token验证通过，允许请求继续
            return true;
        } else {
            // Token验证失败，返回未授权的响应
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "{\"错误\": \"令牌丢失...\"}");
            return false;
        }
    }
    // 设置错误响应的方法
    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(message);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 在请求处理之后进行操作，但在视图渲染之前（Controller方法调用之后）
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 在整个请求完成之后进行操作，包括视图渲染完成之后
    }

    private boolean isValidToken(String authorizationHeader) {
        // 在这里进行Token验证逻辑，例如验证Token的签名、过期时间等。
        // 如果验证通过，返回true，否则返回false。
        // 您可以根据实际情况使用Spring Security或其他库来简化此过程。
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 去掉 Bearer 前缀
            String token = authorizationHeader.substring(7);
            // 在这里检查 token 是否存在于 Redis 中
            boolean tokenExists = redisTemplate.hasKey(token);
            return tokenExists; // 如果存在则返回 true，否则返回 false。
        }
        return false;
    }

}
