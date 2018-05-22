package com.tom.clientmgr.controller;

import com.tom.clientmgr.domian.Users;
import com.tom.clientmgr.services.UsersService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsersPermissionController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value="/getUsers",method = RequestMethod.GET)
    public JSONObject getAllUusers(){
        List<Users> users= (List<Users>) usersService.getUsers();

        List list= users.stream().map(o -> {
            List<String> l=new ArrayList();
            l.add(o.getId().toString());
            l.add(o.getName());
            l.add(o.getLastName());
            l.add(o.getPassword());
            l.add(o.getActive().toString());
            l.add(o.getEmail());
            return l;
        }).collect(Collectors.toList());

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",list);
        return jsonObject;
    }
}
