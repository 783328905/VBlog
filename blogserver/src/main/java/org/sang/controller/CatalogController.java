package org.sang.controller;

import org.sang.bean.Catalog;
import org.sang.bean.RespBean;
import org.sang.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin/category")
public class CatalogController {
    @Autowired
    CatalogService catalogService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Catalog> getAllCategories() {
        return catalogService.getAllCategories();
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteById(@PathVariable String ids) {
        boolean result = catalogService.deleteCategoryByIds(ids);
        if (result) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RespBean addNewCate(Catalog catalog) {
        catalog.setCreateTime(new Date());
        int result = catalogService.addCategory(catalog);
        if (result == 1) {
            return new RespBean("success", "添加成功!");
        }
        return new RespBean("error", "添加失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public RespBean updateCate(Catalog catalog) {
        int i = catalogService.updateCategoryById(catalog);
        if (i == 1) {
            return new RespBean("success", "修改成功!");
        }
        return new RespBean("error", "修改失败!");
    }
}
