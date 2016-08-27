package com.rithm.controller;

import com.rithm.entity.Member;
import com.rithm.repository.MemberRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by a9jr5626 on 8/27/16.
 */
@RestController
public class MemberController {

    @Autowired
    MemberRepository memberRepo;

    @RequestMapping(value = "/api/members", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Object> memberAdd (@RequestBody Member member){
        JSONObject entity = new JSONObject();
        try {
            member.setRegistered(new Date(System.currentTimeMillis()));

            Calendar c = Calendar.getInstance();
            c.setTime(member.getRegistered());
            c.add(Calendar.DATE, 365);
            member.setExpired(new Date(c.getTimeInMillis()));

            memberRepo.save(member);
            entity.put("payload", member);
            entity.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            entity.put("message", e.getMessage());
            entity.put("success", false);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/members", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<Object> memberEdit (@RequestBody Member member){
        JSONObject entity = new JSONObject();
        try {
            if (!memberRepo.exists(member.getId())) {
                throw new Exception("Member does not exist : " + member.getId());
            }
            memberRepo.save(member);
            entity.put("payload", member);
            entity.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            entity.put("message", e.getMessage());
            entity.put("success", false);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/members", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> memberFindAll (){
        JSONObject entity = new JSONObject();
        try {
            Iterable memberList = memberRepo.findAll();
            entity.put("payload", memberList);
            entity.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            entity.put("message", e.getMessage());
            entity.put("success", false);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
