package com.tom.clientmgr.controller;

import com.tom.clientmgr.domian.OauthClientDetails;
import com.tom.clientmgr.services.CRUDServiceKeyIsStr;
import com.tom.clientmgr.services.OauthClientDetailsService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class OauthController {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @RequestMapping(value = "/getOauthClientDetails", method = RequestMethod.GET)
    public JSONObject getOauthClientDetails() {
        List<OauthClientDetails> oauthClientDetails = (List<OauthClientDetails>) oauthClientDetailsService.listAll();

        List list = oauthClientDetails.stream().map(o -> {
            List<String> l = new ArrayList();
            l.add(o.getClient_id());
            l.add(o.getResource_ids());
            l.add(o.getClient_secret());
            l.add(o.getScope());
            l.add(o.getAuthorized_grant_types());
            l.add(o.getWeb_server_redirect_uri());
            l.add(o.getAuthorities());
            l.add((o.getAccess_token_validity()==null)?"":o.getAccess_token_validity().toString());
            l.add((o.getRefresh_token_validity()==null)?"":o.getRefresh_token_validity().toString());
            l.add(o.getAdditional_information());
            l.add(o.getAutoapprove());
            return l;
        }).collect(Collectors.toList());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", list);
        return jsonObject;
    }

    @RequestMapping(value = "/createOrUpdateOauthClientDetails", method = RequestMethod.POST)
    public String createOrUpdateOauthClientDetails(@RequestParam("data") String info) {

        Boolean oauthIsExist = false;
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) (new JSONParser().parse(info));
            JSONArray jsonArray = (JSONArray) jsonObject.get("dat");
            if (jsonObject.get("type").equals("create")) {
                return saveOauthClientDetails(jsonArray);
            }else if(jsonObject.get("type").equals("update")){
                return saveOauthClientDetails(jsonArray);
            }else{
                return "后台状态符缺失！";
            }
        } catch (ParseException e) {
            return "请检查输入数据类型！";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    private String saveOauthClientDetails(JSONArray jsonArray) {
        if (jsonArray.size() > 0) {
            jsonArray.stream().map(ja -> {
                JSONArray jsa = (JSONArray) ja;
                OauthClientDetails oauthClientDetails = new OauthClientDetails(
                        jsa.get(0).toString(),
                        jsa.get(1).toString(),
                        jsa.get(2).toString(),
                        jsa.get(3).toString(),
                        jsa.get(4).toString(),
                        jsa.get(5).toString(),
                        jsa.get(6).toString(),
                        jsa.get(7).toString().equals("")?null:Integer.parseInt(jsa.get(7).toString()),
                        jsa.get(8).toString().equals("")?null:Integer.parseInt(jsa.get(8).toString()),
                        jsa.get(9).toString(),
                        jsa.get(10).toString());
                return oauthClientDetails;
            }).forEach(u -> oauthClientDetailsService.saveOrUpdate((OauthClientDetails) u));
            return "操作成功！";
        }else{
            return "数据为空！";
        }
    }

    @RequestMapping(value = "/deleteOauthClientDetails", method = RequestMethod.POST)
    public String deleteOauthClientDetails(@RequestParam("deleteIds") String deleteIds) {
        return deleteObject(deleteIds,oauthClientDetailsService);
    }

    private String deleteObject(@RequestParam("deleteIds") String deleteIds, CRUDServiceKeyIsStr service) {
        try {
            JSONArray jsonArray = (JSONArray) (new JSONParser().parse(deleteIds));
            jsonArray.stream().map(j->{
                return j.toString();
            }).forEach(id->service.delete(id.toString()));
            return "success";
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
