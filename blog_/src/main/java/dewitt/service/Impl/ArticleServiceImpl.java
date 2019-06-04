package dewitt.service.Impl;

import dewitt.dao.ArticleDao;
import dewitt.entity.Article;
import dewitt.service.ArticleService;
import dewitt.utils.userUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Override
    public int addNewArticle(Article article) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        article.setCreateTime(timestamp);
        article.setPageView(0);
        article.setUsername(userUtil.getCurrentUser().getName());
        int result = articleDao.addNewArticle(article);
        if (result==1)return 1;
        else return 0;
    }

    @Override
    public List<Article> getArticleList(int start, int end) {
        return articleDao.getArticleList(start,end);
    }

    @Override
    public List<Article> getArticleListByUsername(String username,int page,int pagee) {
        return  articleDao.getArticleListByUsername(username,page,pagee);
    }

    @Override
    public int updateArticleView(int id) {
        return articleDao.updateArticle(id);
    }

    @Override
    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    @Override
    public int deleteArticleById(int id) {
        return articleDao.deleteArticleById(id);
    }

    @Override
    public int getArticleCountSum() {
        return articleDao.getArticleCountSum();
    }

    @Override
    public int getArticleCount(String username) {
        return articleDao.getArticleCount(username);
    }
}
