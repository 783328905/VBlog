package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.bean.Catalog;
import org.sang.bean.User;

import java.util.List;

/**
 * Created by sang on 2017/12/19.
 */
@Mapper
public interface CatalogMapper {
    List<Catalog> getAllCatalogs();

    int deleteCatalogByIds(@Param("ids") String[] ids);

    int updateCatalogById(Catalog catalog);

    int addCatalog(@Param("catalog") Catalog catalog,@Param("user") User user);
}
