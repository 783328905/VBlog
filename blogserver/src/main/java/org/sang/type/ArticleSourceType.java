package org.sang.type;

import org.sang.utils.HasDefaultInterface;
import org.sang.utils.HasValueInterface;

/**
 * Created by caiping on 2019/11/14.
 */
public enum  ArticleSourceType implements HasDefaultInterface<ArticleSourceType>{
    web_page(1),
    backend(2),
    csdn(3),
    jianshu(4),
    cnblogs(5)
    ;
    private Integer value;

    ArticleSourceType(Integer value){
        this.value = value;
    }

    @Override
    public ArticleSourceType getDefault() {
        return web_page;
    }


    @Override
    public Integer getValue() {
        return value;
    }

    public static ArticleSourceType valueOf(Integer value){
        for (ArticleSourceType ArticleSourceType : ArticleSourceType.values()) {
            if(ArticleSourceType.getValue().equals(value)){
                return ArticleSourceType;
            }
        }
        throw new IllegalArgumentException("unknown ArticleSourceType, value:"+value);
    }

}
