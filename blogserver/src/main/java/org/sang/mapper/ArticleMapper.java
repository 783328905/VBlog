package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.bean.Article;

import java.util.List;

/**
 * Created by sang on 2017/12/20.
 */
@Mapper
public interface ArticleMapper {
    int addNewArticle(Article article);

    int updateArticle(Article article);

    List<Article> getArticleByState(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count,@Param("keywords") String keywords);

//    List<Article> getArticleByStateByAdmin(@Param("start") int start, @Param("count") Integer count, @Param("keywords") String keywords);

    int getArticleCountByState(@Param("state") Integer state, @Param("uid") Integer uid, @Param("keywords") String keywords);

    int updateArticleState(@Param("aids") Integer aids[], @Param("state") Integer state);

    int deleteArticleById(@Param("aids") Integer[] aids);

    Article getArticleById(Integer aid);

    void pvIncrement(Integer aid);

    //INSERT INTO pv(countDate,pv,uid) SELECT NOW(),SUM(pageView),uid FROM article GROUP BY uid
    void pvStatisticsPerDay();

    List<String> getCategories(Integer uid);

    List<Integer> getDataStatistics(Integer uid);
}
