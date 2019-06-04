package dewitt.service;

import dewitt.entity.Article;

import java.util.List;

public interface ArticleService {
    int addNewArticle(Article article);
    List<Article> getArticleList(int start,int end);
    List<Article> getArticleListByUsername(String username,int page,int pagee);
    int updateArticleView(int id);
    Article getArticleById(int id);
    int deleteArticleById(int id);
    int getArticleCountSum();
    int getArticleCount(String username);
}
