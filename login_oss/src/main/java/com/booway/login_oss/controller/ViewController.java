package com.booway.login_oss.controller;

import com.booway.login_oss.pojo.User;
import com.booway.login_oss.util.LoginCacheUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * @author 罗超
 * @company 杭州声讯科技有限公司
 * @createtime 2020-08-08-14:40
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @RequestMapping("/login")
    public String toLogin(@RequestParam(required = false, defaultValue = "") String target, HttpSession session, @CookieValue(value = "TOKEN",required = false) Cookie cookie)
    {

        if (StringUtils.isEmpty(target))
        {
            target = "http://www.booway.com:8081/view/index";
        }

        if (cookie != null)
        {
            //如果是已经登录了的用户，就直接跳转到首页
            String token = cookie.getValue();
            User user = LoginCacheUtils.userMap.get(token);
            if (user != null)
            {
                return "redirect:" + target;
            }
        }
        //作为登录回调地址
        //todo 这里需要做地址是否合法的校验
            session.setAttribute("target",target);
            return "login";
    }

}
