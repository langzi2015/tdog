package com.lirb.tdog.web;

import com.lirb.tdog.pojo.User;
import com.lirb.tdog.service.UserService;
import com.lirb.tdog.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                     @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        return userService.list(start,size,5);
    }
}
