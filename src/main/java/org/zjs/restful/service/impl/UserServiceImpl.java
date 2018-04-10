package org.zjs.restful.service.impl;

import org.springframework.stereotype.Service;
import org.zjs.restful.dataobject.UserDO;
import org.zjs.restful.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018/4/9
 * Time: 16:21
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private Map<Integer, UserDO> userDB = new HashMap<>();

    @Override
    public UserDO getUserById(int uid) {
        return userDB.get(uid);
    }

    @Override
    public UserDO addUser(String name, int age, String gender) {
        Random r = new Random();
        int uid = r.nextInt(99999999);
        UserDO user = new UserDO();
        user.setUid(uid);
        user.setName(name);
        user.setAge(age);
        user.setGender(gender);
        userDB.put(uid, user);
        return user;
    }

    @Override
    public UserDO updateUser(int uid, String name, Integer age, String gender) {
        UserDO user = userDB.get(uid);
        if(user == null) {
            return null;
        }
        if(name != null) {
            user.setName(name);
        }
        if(age != null) {
            user.setAge(age);
        }
        if(gender != null) {
            user.setGender(gender);
        }
        userDB.put(uid, user);
        return user;
    }

    @Override
    public String deleteUser(int uid) {
        if(!userDB.containsKey(uid)) {
            return "{\"isSuccess\":false}";
        }
        userDB.remove(uid);
        return "{\"isSuccess\":true}";
    }
}
