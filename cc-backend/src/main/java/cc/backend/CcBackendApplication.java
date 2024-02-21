package cc.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"cc.backend.user.mapper","cc.backend.manager.mapper","cc.backend.common.token"})
public class CcBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcBackendApplication.class, args);
    }

}
