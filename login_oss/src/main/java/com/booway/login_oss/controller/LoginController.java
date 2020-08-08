package com.booway.login_oss.controller;

import com.booway.login_oss.pojo.User;
import com.booway.login_oss.util.LoginCacheUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author 罗超
 * @company 杭州声讯科技有限公司
 * @createtime 2020-08-08-15:27
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Set<User> users;
    static {
        users = new HashSet<>();
        users.add(new User(101,"zhangsan","123456"));
        users.add(new User(102,"lisi","123456"));
        users.add(new User(103,"wangwu","123456"));
    }
    @PostMapping
    public String doLogin(String userName, String password, HttpSession session, HttpServletResponse response)
    {
        String target = (String)session.getAttribute("target");
        Optional<User> first = users.stream().filter(user -> user.getUserName().equals(userName) &&
                                                             user.getPassword().equals(password)).findFirst();

        if (first.isPresent()){
            //用户名和密码正确
            String token  = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("TOKEN",token);
            cookie.setDomain("booway.com");
            response.addCookie(cookie);
            LoginCacheUtils.userMap.put(token,first.get());
        }else {
            //登录失败
            session.setAttribute("msg","用户名或密码错误");
            return "login";
        }
        return "redirect:" + target;
    }

    @GetMapping("info")
    @ResponseBody
    public ResponseEntity<User> getInfo(String token)
    {
        if (!StringUtils.isEmpty(token))
        {
            User user = LoginCacheUtils.userMap.get(token);
            return ResponseEntity.ok(user);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
