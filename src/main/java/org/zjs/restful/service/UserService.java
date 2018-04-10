package org.zjs.restful.service;

import org.zjs.restful.dataobject.UserDO;

/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018/4/9
 * Time: 16:17
 */
public interface UserService {
    UserDO getUserById(int uid);
    UserDO addUser(String name, int age, String gender);
    UserDO updateUser(int uid, String name, Integer age, String gender);
    String deleteUser(int uid);
}
