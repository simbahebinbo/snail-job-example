package com.example.controller;

import com.aizuda.snailjob.server.model.dto.CallbackParamsDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "工作流回调", description = "工作流回调")
public class WorkflowCallbackController {

    @PostMapping
    public void callback(@RequestBody List<CallbackParamsDTO> object, @RequestHeader HttpHeaders headers) {
        log.info("callback: {}, secret:{} secret:{}", object, "secret", headers.getFirst("secret"));
    }
}
