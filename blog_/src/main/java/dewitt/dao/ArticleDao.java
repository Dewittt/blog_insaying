package dewitt.dao;

import dewitt.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    int addNewArticle(Article article);
    int deleteArticleById(int id);
    int updateArticle(int id);
    Article getArticleById(@Param("id") int id);
    List<Article> getArticleList(@Param("start") int start,@Param("end") int end);
    List<Article> getArticleListByUsername(@Param("username") String username,@Param("page") int page,@Param("pagee") int pagee);
    int getArticleCountSum();
    int getArticleCount(@Param("username") String username);
}
