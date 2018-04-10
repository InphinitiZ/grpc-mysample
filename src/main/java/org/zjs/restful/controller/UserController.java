package org.zjs.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zjs.restful.dataobject.UserDO;
import org.zjs.restful.service.UserService;

/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018/4/9
 * Time: 16:21
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/{uid}")
    UserDO getUser(@PathVariable Integer uid) {
        return userService.getUserById(uid);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{uid}")
    String deleteUser(@PathVariable Integer uid) {
        return userService.deleteUser(uid);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{uid}")
    UserDO updateUser(@PathVariable Integer uid, String name, Integer age, String gender) {
        return userService.updateUser(uid, name, age, gender);
    }

    @RequestMapping(method = RequestMethod.POST)
    UserDO addUser(String name, int age, String gender) {
        return userService.addUser(name, age, gender);
    }
}
