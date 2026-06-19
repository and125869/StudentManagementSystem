package com.sms.controller;

import com.sms.entity.Account;
import com.sms.service.AuthService;
import com.sms.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 */
@Controller
public class LoginController {

    @Autowired
    private AuthService authService;

    /**
     * 登录页面
     */
    @GetMapping({"/", "/login"})
    public String loginPage() {
        return "login";
    }

    /**
     * 处理登录请求
     */
    @PostMapping("/doLogin")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpSession session,
                          Model model) {
        AuthService.LoginResult result = authService.login(username, password);

        if (result.isSuccess()) {
            Account account = result.getAccount();
            AuthUtil.setLoginUser(session, account);

            // 根据角色跳转到不同主页
            switch (account.getRoleType()) {
                case "student":
                    return "redirect:/student/main";
                case "teacher":
                    return "redirect:/teacher/main";
                case "academic_admin":
                case "system_admin":
                    return "redirect:/admin/main";
                default:
                    return "redirect:/login";
            }
        } else {
            model.addAttribute("error", result.getMessage());
            model.addAttribute("username", username);
            return "login";
        }
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        AuthUtil.clearLogin(session);
        return "redirect:/login";
    }

    /**
     * 修改密码页面
     */
    @GetMapping("/changePassword")
    public String changePasswordPage() {
        return "changePassword";
    }

    /**
     * 处理修改密码
     */
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("confirmPassword") String confirmPassword,
                                  HttpSession session,
                                  Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "两次输入的新密码不一致");
            return "changePassword";
        }

        String userId = (String) session.getAttribute("userId");
        boolean success = authService.changePassword(userId, oldPassword, newPassword);

        if (success) {
            model.addAttribute("success", "密码修改成功");
        } else {
            model.addAttribute("error", "原密码错误");
        }
        return "changePassword";
    }
}