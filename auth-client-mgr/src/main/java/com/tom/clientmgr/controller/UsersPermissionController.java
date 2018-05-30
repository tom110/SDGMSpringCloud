package com.tom.clientmgr.controller;

import com.rabbitmq.client.AMQP;
import com.tom.clientmgr.domian.Authority;
import com.tom.clientmgr.domian.Role;
import com.tom.clientmgr.domian.Users;
import com.tom.clientmgr.repository.AuthorityRepository;
import com.tom.clientmgr.services.AuthorityService;
import com.tom.clientmgr.services.CRUDService;
import com.tom.clientmgr.services.RoleService;
import com.tom.clientmgr.services.UsersService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UsersPermissionController {
    private Logger log = LogManager.getLogger(UsersPermissionController.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

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
        return deleteObject(deleteIds,usersService);
    }

    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    public JSONObject getRoles() {
        List<Role> roles = (List<Role>) roleService.listAll();

        List list = roles.stream().map(o -> {
            List<String> l = new ArrayList();
            l.add(o.getRoleId().toString());
            l.add(o.getRole());
            l.add(o.getRoleName());
            l.add(o.getParentId().toString());
            return l;
        }).collect(Collectors.toList());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", list);
        return jsonObject;
    }

    @RequestMapping(value = "/createOrUpdateRoles", method = RequestMethod.POST)
    public String createOrUpdateRoles(@RequestParam("data") String userInfo) {

        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) (new JSONParser().parse(userInfo));
            JSONArray jsonArray = (JSONArray) jsonObject.get("dat");
            if (jsonObject.get("type").equals("create")) {
                if (jsonArray.size() > 0) {
                    jsonArray.stream().map(ja -> {
                        JSONArray jsa = (JSONArray) ja;
                        Role role = new Role(
                                jsa.get(1).toString(),
                                jsa.get(2).toString(),
                                Integer.parseInt(jsa.get(3).toString()));
                        return role;
                    }).forEach(u -> roleService.saveOrUpdate((Role) u));
                    return "操作成功！";
                }else{
                    return "数据为空！";
                }
            }else if(jsonObject.get("type").equals("update")){
                if (jsonArray.size() > 0) {
                    jsonArray.stream().map(ja -> {
                        JSONArray jsa = (JSONArray) ja;
                        Role role = new Role(
                                Integer.parseInt(jsa.get(0).toString()),
                                jsa.get(1).toString(),
                                jsa.get(2).toString(),
                                Integer.parseInt(jsa.get(3).toString()));
                        return role;
                    }).forEach(u -> roleService.saveOrUpdate((Role) u));
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

    @RequestMapping(value = "/deleteRoles", method = RequestMethod.POST)
    public String deleteRoles(@RequestParam("deleteIds") String deleteIds) {
        return deleteObject(deleteIds,roleService);
    }

    private String deleteObject(@RequestParam("deleteIds") String deleteIds, CRUDService service) {
        try {
            JSONArray jsonArray = (JSONArray) (new JSONParser().parse(deleteIds));
            jsonArray.stream().map(j->{
                return Integer.parseInt(j.toString());
            }).forEach(id->service.delete((Integer) id));
            return "success";
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/getRolesByUserId/{userId}", method = RequestMethod.POST)
    public List<Integer> getRolesByUserId(@PathVariable("userId") String userId) {
        Users users= usersService.getById(Integer.parseInt(userId));
        return users.getRoles().stream().map( r->{
            return r.getRoleId();
        } ).collect(Collectors.toList());
    }

    @RequestMapping(value = "/userBindRoles/{userId}", method = RequestMethod.POST)
    public String userBindRoles(@PathVariable("userId") Integer userId,@RequestParam("roles") String roleIds) {
        try {
            usersService.userBindRoles(userId,roleIds);
            return "绑定成功！";
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping(value = "/getAuthorities", method = RequestMethod.GET)
    public JSONObject getAuthorities() {
        try {
            List<Authority> authorities = (List<Authority>) authorityService.listAll();

            List list = authorities.stream().map(o -> {
                List<String> l = new ArrayList();
                l.add(o.getId().toString());
                l.add(o.getAuthority());
                l.add(o.getAuthorityName());
                l.add(o.getParentId().toString());
                return l;
            }).collect(Collectors.toList());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            return jsonObject;
        }catch (Exception e){
            return null;
        }
    }

    @RequestMapping(value = "/createOrUpdateAuthority", method = RequestMethod.POST)
    public String createOrUpdateAuthority(@RequestParam("data") String authorityInfo) {

        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) (new JSONParser().parse(authorityInfo));
            JSONArray jsonArray = (JSONArray) jsonObject.get("dat");
            if (jsonObject.get("type").equals("create")) {
                if (jsonArray.size() > 0) {
                    jsonArray.stream().map(ja -> {
                        JSONArray jsa = (JSONArray) ja;
                        Authority authority = new Authority(
                                jsa.get(1).toString(),
                                jsa.get(2).toString(),
                                Integer.parseInt((String) jsa.get(3)));
                        return authority;
                    }).forEach(u -> authorityService.saveOrUpdate((Authority) u));
                    return "操作成功！";
                }else{
                    return "数据为空！";
                }
            }else if(jsonObject.get("type").equals("update")){
                if (jsonArray.size() > 0) {
                    jsonArray.stream().map(ja -> {
                        JSONArray jsa = (JSONArray) ja;
                        Authority authority = new Authority(
                                Integer.parseInt(jsa.get(0).toString()),
                                jsa.get(1).toString(),
                                jsa.get(2).toString(),
                                Integer.parseInt((String) jsa.get(3)));
                        return authority;
                    }).forEach(u -> authorityService.saveOrUpdate((Authority)u));
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

    @RequestMapping(value = "/deleteAuthority", method = RequestMethod.POST)
    public String deleteAuthority(@RequestParam("deleteIds") String deleteIds) {
        return deleteObject(deleteIds,authorityService);
    }

    @RequestMapping(value = "/getAuthoritiesByRoleId/{roleId}", method = RequestMethod.POST)
    public List<Integer> getAuthoritiesByRoleId(@PathVariable("roleId") String roleId) {
        Role role= roleService.getById(Integer.parseInt(roleId));
        return role.getAuthorities().stream().map( r->{
            return r.getId();
        } ).collect(Collectors.toList());
    }

    @RequestMapping(value = "/roleBindAuthorities/{roleId}", method = RequestMethod.POST)
    public String roleBindAuthorities(@PathVariable("roleId") Integer roleId,@RequestParam("authoritiesIds") String authoritiesIds) {
        try {
            roleService.roleBindAuthorities(roleId,authoritiesIds);
            return "绑定成功！";
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }


}
