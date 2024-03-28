package com.sample.single.application.controler;

import com.lrenyi.template.core.util.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    
    @Value("${test.password}")
    private String plain;
    
    @GetMapping("/not/apply/security")
    public Result<String> notApplySecurity() {
        return Result.getSuccess("这是没有使用security的接口示例");
    }
    
    @GetMapping("/password/encoder")
    public Result<String> passwordEncoder() {
        return Result.getSuccess(plain);
    }
    
    @GetMapping("/apply/security")
    public Result<String> applySecurity() {
        return Result.getSuccess("这是使用security的接口示例");
    }
}
