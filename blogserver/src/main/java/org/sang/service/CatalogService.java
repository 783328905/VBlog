package org.sang.service;

import org.sang.bean.Catalog;
import org.sang.bean.Category;
import org.sang.mapper.CatalogMapper;
import org.sang.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sang on 2017/12/19.
 */
@Service
@Transactional
public class CatalogService {
    @Autowired
    CatalogMapper catalogMapper;

    public List<Catalog> getAllCategories() {
        return catalogMapper.getAllCatalogs();
    }

    public boolean deleteCategoryByIds(String ids) {
        String[] split = ids.split(",");
        int result = catalogMapper.deleteCatalogByIds(split);
        return result == split.length;
    }

    public int updateCategoryById(Catalog catalog) {
        return catalogMapper.updateCatalogById(catalog);
    }

    public int addCategory(Catalog catalog) {
        return catalogMapper.addCatalog(catalog, Util.getCurrentUser());
    }
}
