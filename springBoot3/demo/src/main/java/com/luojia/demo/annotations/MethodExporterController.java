package com.luojia.demo.annotations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class MethodExporterController {

    @MethodExporter
    @GetMapping("/method/list")
    public Map list(@RequestParam Integer page,
                    @RequestParam Integer rows) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("message", "success");
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
