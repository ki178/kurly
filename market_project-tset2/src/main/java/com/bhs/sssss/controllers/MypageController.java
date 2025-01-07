package com.bhs.sssss.controllers;

import com.bhs.sssss.entities.MemberEntity;
import com.bhs.sssss.results.CommonResult;
import com.bhs.sssss.results.Result;
import com.bhs.sssss.services.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/mypage")
public class MypageController {
    private final MemberService memberService;

    @Autowired
    public MypageController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getInfo(@SessionAttribute(value = "member", required = false) MemberEntity member) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("member", member);
        if(member == null){
            modelAndView.setViewName("redirect:/member/login");
        } else {
            modelAndView.setViewName("mypage/info");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/password-check", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postPasswordCheck(@SessionAttribute(value = "member", required = false) MemberEntity member,
                                    @RequestParam(value = "password", required = false) String password) {
        JSONObject response = new JSONObject();
        Result result = this.memberService.passwordCheck(member, password);
        response.put(Result.NAME, result.nameToLower());
        return response.toString();
    }

    @RequestMapping(value = "/info/modify", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getInfoModify(@SessionAttribute(value = "member", required = false) MemberEntity member) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("member", member);
        if(member == null){
            modelAndView.setViewName("redirect:/member/login");
        } else {
            modelAndView.setViewName("mypage/info.modify");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/info/modify", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchInfoModify(HttpSession session, MemberEntity member) {
        Result result = this.memberService.updateInfoModify(member);
        JSONObject response = new JSONObject();
        response.put(Result.NAME, result.nameToLower());
        session.setAttribute("member", null);

        return response.toString();
    }

    @RequestMapping(value = "/leave", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getLeave(@SessionAttribute(value = "member", required = false) MemberEntity member) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("member", member);
        modelAndView.setViewName("mypage/leave");
        return modelAndView;
    }

    @RequestMapping(value = "/leave", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchLeave(@SessionAttribute(value = "member", required = false) MemberEntity member,
                             @RequestParam(value = "password", required = false) String password) {
        Result result = this.memberService.leaveMember(member, password);
        JSONObject response = new JSONObject();
        response.put(Result.NAME, result.nameToLower());
        return response.toString();
    }

    @RequestMapping(value = "/pick", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getPick(@SessionAttribute(value = "member", required = false) MemberEntity member) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("member", member);
        modelAndView.setViewName("mypage/pick");
        return modelAndView;
    }

}
