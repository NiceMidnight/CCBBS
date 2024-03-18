package cc.backend.manager.controller;

import cc.backend.manager.service.impl.LogsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
@RestController
@RequestMapping("/manager/log")
public class LogsController
{
    @Autowired
    private LogsServiceImpl logsService;

}
