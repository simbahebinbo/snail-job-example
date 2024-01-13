package com.example.easy.retry.controller;

import com.aizuda.easy.retry.server.model.dto.CallbackParamsDTO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiaowoniu
 * @date 2024-01-03 21:09:14
 * @since 2.6.0
 */
@RestController
@RequestMapping("/workflow/callback")
@Slf4j
public class WorkflowCallbackController {

    @PostMapping
    public void callback(@RequestBody List<CallbackParamsDTO> object, @RequestHeader HttpHeaders headers) {
        log.info("callback: {}, secret:{} secret:{}", object, "secret", headers.getFirst("secret"));
    }
}
