package org.sang.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by caiping on 2019/12/11.
 */
public class ExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    public static<T> void toExcel(OutputStream outputStream, List<T> objs, String [] headers){
        HSSFWorkbook workbook = new HSSFWorkbook();
        String fileName = "文章信息"+DateUtils.DateToString(new Date(),DateUtils.DATE_TO_STRING_SHORT_PATTERN)+ ".xls";
        HSSFSheet sheet = workbook.createSheet(fileName);
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString textString = new HSSFRichTextString(headers[i]);
            cell.setCellValue(textString);
        }
        Gson gson = new Gson();

        String jsonArray = gson.toJson(objs);
        JsonArray asJsonArray = new JsonParser().parse(jsonArray).getAsJsonArray();
        for(int i=0;i<asJsonArray.size();i++){
            row = sheet.createRow(i+1);
            JsonObject jsonObject = asJsonArray.get(i).getAsJsonObject();
            for(int index =0;i<headers.length;i++){
                row.createCell(index).setCellValue(jsonObject.get(headers[index])+"");
            }
        }

        try{

            workbook.write(outputStream);
            logger.info("导出excel成功");
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
