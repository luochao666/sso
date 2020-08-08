package com.booway.vip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 罗超
 * @company 杭州声讯科技有限公司
 * @createtime 2020-08-08-15:06
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String URL = "http://login.booway.com:8080/login/info?token=";

    @RequestMapping("/index")
    public String toIndex(@CookieValue(value = "TOKEN", required = false) Cookie cookie, HttpSession session)
    {
        if (cookie != null)
        {
            String token = cookie.getValue();
            if (!StringUtils.isEmpty(token))
            {
                Map result = restTemplate.getForObject(URL + token, Map.class);
                session.setAttribute("loginUser",result);
            }
        }
        return "index";
    }

}
