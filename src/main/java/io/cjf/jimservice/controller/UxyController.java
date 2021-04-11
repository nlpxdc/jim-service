package io.cjf.jimservice.controller;

import io.cjf.jimservice.dto.in.UxIdInDTO;
import io.cjf.jimservice.dto.in.UyIdInDTO;
import io.cjf.jimservice.po.Uxy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uxy")
public class UxyController {

    @PostMapping("/loadByUy")
    public Uxy loadByUy(@RequestBody UyIdInDTO uyIdInDTO) {
        return null;
    }

    @PostMapping("/loadByUx")
    public Uxy loadByUx(@RequestBody UxIdInDTO uxIdInDTO) {
        return null;
    }

    @PostMapping("/applyFriend")
    public void applyFriend(@RequestBody UyIdInDTO uyIdInDTO) {

    }

}
