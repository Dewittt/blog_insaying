package dewitt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import dewitt.entity.User;
import dewitt.service.UserService;
import dewitt.utils.userUtil;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@ComponentScan({"/spring/*.xml","/mapper/*.xml","/mybatis-config.xml"})
public class UserController {

    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @PostMapping("/uploadimg")
    public String uploadImg(@RequestParam("file") MultipartFile image, Model model,Principal principal) {
        String url = "/img/userImg/";
        String filePath = "/home/dewitt/blog";
        File imgFolder = new File(filePath);
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url+=imgName;
            int result = userService.updateUserImg(principal.getName(),url);
            if (result==1)model.addAttribute("success", "上传成功！");
            return "redirect:/index";
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("failure", "上传失败！");
        return "redirect:/settings";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String deng(){
        return "login";
    }


    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String reg(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        int result = userService.reg(user);
        if (result==0){
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authenticatedUser = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());
            return "redirect:/index";
        }
        else if (result==1){
            return "redirect:/filer";
        }
        else {

            return "redirect:/filer";
        }
    }

    @RequestMapping("/index")
    public String index(){
        return "redirect:/article/page/0";
    }


    @RequestMapping("/filer")
    public String filer(){return "filer";}

    @RequestMapping(value = "/write",method = RequestMethod.GET)
    public String write(){return "write";}

    @RequestMapping(value = "/settings",method = RequestMethod.GET)
    public String setting(Model model,Principal principal){
        String username = principal.getName();
        User user = userService.getUser(username);
        model.addAttribute("currentusername",username);
        model.addAttribute("username",username);
        model.addAttribute("currentuserimg",user.getImg());
        model.addAttribute("myshort",user.getMyShort());
        return "settings";
    }

    @RequestMapping(value = "/settings/short",method = RequestMethod.POST)
    public String setshort(Model model,Principal principal,@RequestParam("setshort") String setshort){
       int result = userService.updateUsershort(principal.getName(),setshort);
       if (result==1){
           model.addAttribute("设置成功");
           return "redirect:/index";
       }
       else {
           model.addAttribute("设置失败");
           return "/settings";
       }
    }

}
