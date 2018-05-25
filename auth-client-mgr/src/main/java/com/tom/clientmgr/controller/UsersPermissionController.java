package com.tom.clientmgr.controller;

import com.tom.clientmgr.domian.Users;
import com.tom.clientmgr.services.UsersService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsersPermissionController {
    private Logger log = LogManager.getLogger(UsersPermissionController.class);

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public JSONObject getAllUusers() {
        List<Users> users = (List<Users>) usersService.listAll();

        List list = users.stream().map(o -> {
            List<String> l = new ArrayList();
            l.add(o.getId().toString());
            l.add(o.getName());
            l.add(o.getLastName());
            l.add(o.getPassword());
            l.add(o.getActive().toString());
            l.add(o.getEmail());
            return l;
        }).collect(Collectors.toList());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", list);
        return jsonObject;
    }

    @RequestMapping(value = "/createOrUpdateUsers", method = RequestMethod.POST)
    public String createOrUpdateUsers(@RequestParam("data") String userInfo) {

        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) (new JSONParser().parse(userInfo));
            JSONArray jsonArray = (JSONArray) jsonObject.get("dat");
            if (jsonObject.get("type").equals("create")) {
                if (jsonArray.size() > 0) {
                    jsonArray.stream().map(ja -> {
                        JSONArray jsa = (JSONArray) ja;
                        Users users = new Users(
                                jsa.get(1).toString(),
                                jsa.get(2).toString(),
                                jsa.get(3).toString(),
                                Integer.parseInt((String) jsa.get(4)),
                                jsa.get(5).toString());
                        return users;
                    }).forEach(u -> usersService.saveOrUpdate((Users)u));
                    return "操作成功！";
                }else{
                    return "数据为空！";
                }
            }else if(jsonObject.get("type").equals("update")){
                if (jsonArray.size() > 0) {
                    jsonArray.stream().map(ja -> {
                        JSONArray jsa = (JSONArray) ja;
                        Users users = new Users(
                                Integer.parseInt(jsa.get(0).toString()),
                                jsa.get(1).toString(),
                                jsa.get(2).toString(),
                                jsa.get(3).toString(),
                                Integer.parseInt((String) jsa.get(4)),
                                jsa.get(5).toString());
                        return users;
                    }).forEach(u -> usersService.saveOrUpdate((Users)u));
                    return "操作成功！";
                }else{
                    return "数据为空！";
                }
            }else{
                return "后台状态符缺失！";
            }
        } catch (ParseException e) {
            return "请检查输入数据类型！";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/deleteUsers", method = RequestMethod.POST)
    public String deleteUsers(@RequestParam("deleteIds") String deleteIds) {

        try {
            JSONArray jsonArray = (JSONArray) (new JSONParser().parse(deleteIds));

            jsonArray.stream().map(j->{
                return Integer.parseInt(j.toString());
            }).forEach(id->usersService.delete((Integer) id));

            return "success";

        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

}
