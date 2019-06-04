package dewitt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import dewitt.entity.Article;
import dewitt.entity.User;
import dewitt.service.ArticleService;
import dewitt.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;


@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;



    @PostMapping("/")
    public void addNewArticle(@RequestBody String json, Model model) {
        Article article = JSON.parseObject(json, new TypeReference<Article>() {
        });
//        article.setShortcut(article.getHtmlContent().substring(0,35));
        int result = articleService.addNewArticle(article);
        if (result == 1) {
            model.addAttribute("success", "发布成功！");
        } else {
            model.addAttribute("failure", "发布失败");
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void deleteArticle(Model model, @PathVariable int id) {
        int result = articleService.deleteArticleById(id);
        if (result == 1) model.addAttribute("success", "删除成功");
        else model.addAttribute("failure", "删除失败");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String getArticleById(@PathVariable int id,Model model,Principal principal){
        Article article = articleService.getArticleById(id);
        User user = userService.getUser(article.getUsername());
        model.addAttribute("htmlContent",article.getHtmlContent());
        model.addAttribute("title",article.getTitle());
        model.addAttribute("createtime",article.getCreateTime());
        model.addAttribute("username",principal.getName());
        model.addAttribute("currentusername",article.getUsername());
        model.addAttribute("userid",user.getId());
        model.addAttribute("currentuserimg",user.getImg());
        model.addAttribute("myshort",user.getMyShort());
        return "article";
    }

    @RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
    public String getArticleList(@PathVariable int id, Model model, Principal principal,HttpServletRequest request) {
        List<Article> list = articleService.getArticleList(id*5, 5);
        List<Map<String, String>> rlist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> article = new HashMap<>();
            Article article1 = list.get(i);
            User user = userService.getUser(article1.getUsername());
            article.put("title", article1.getTitle());
            article.put("username", article1.getUsername());
            article.put("shortcut",article1.getShortcut());
            article.put("userimg",user.getImg());
            article.put("articleId", String.valueOf(article1.getId()));
            article.put("userId",String.valueOf(user.getId()));
            rlist.add(article);
        }
        int pages = articleService.getArticleCountSum();
        User currentuser = userService.getUser(principal.getName());
        model.addAttribute("list", rlist);
        model.addAttribute("username", principal.getName());
        model.addAttribute("userid",userService.getUser(principal.getName()).getId());
        model.addAttribute("myshort",currentuser.getMyShort());
        model.addAttribute("currentuserimg",currentuser.getImg());
        model.addAttribute("current",id);
        model.addAttribute("pages",pages%5==0?pages/5:pages/5+1);
        model.addAttribute("url","/article/page/");
        model.addAttribute("currentusername", currentuser.getUsername());
        return "index";
    }


}