package dewitt.controller;

import dewitt.entity.Article;
import dewitt.entity.User;
import dewitt.service.ArticleService;
import dewitt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class PersonController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @RequestMapping("/{id}/{page}")
    public String person(@PathVariable int id, @PathVariable int page, Principal principal, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("currentusername",principal.getName());
        model.addAttribute("username",user.getUsername());
        List<Article> list = articleService.getArticleListByUsername(user.getUsername(),page*5,5);
        List<Map<String, String>> rlist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> article = new HashMap<>();
            Article articletity = list.get(i);
            User user1 = userService.getUser(articletity.getUsername());
            article.put("title", articletity.getTitle());
            article.put("username", articletity.getUsername());
            article.put("shortcut",articletity.getShortcut());
            article.put("userimg",user1.getImg());
            article.put("articleId", String.valueOf(articletity.getId()));
            article.put("userId",String.valueOf(user1.getId()));
            rlist.add(article);
        }
        int pages = articleService.getArticleCount(user.getUsername());
        model.addAttribute("list", rlist);
        model.addAttribute("currentuserimg",user.getImg());
        model.addAttribute("myshort",user.getMyShort());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("current",page);
        model.addAttribute("pages",pages%5==0?pages/5:pages/5+1);
        model.addAttribute("url","/user/"+id+"/");
        return "index";
    }
}