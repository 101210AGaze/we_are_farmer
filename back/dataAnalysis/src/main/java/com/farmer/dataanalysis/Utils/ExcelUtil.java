package com.farmer.dataanalysis.Utils;

import com.farmer.dataanalysis.bean.spiderBeans.mList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public abstract class ExcelUtil {


    public static void export(File filePath , int total, List<mList> data) {
        Workbook wb = new HSSFWorkbook();

        Sheet sheet1 = wb.createSheet(TimeUtil.format(System.currentTimeMillis(),"yyyyMMdd"));
        //Sheet sheet1 = wb.createSheet("0824");
        Row row = sheet1.createRow(0);
        row.createCell(0).setCellValue("市场名称");
        row.createCell(1).setCellValue("省份名称");
        row.createCell(2).setCellValue("市区名称");
        row.createCell(3).setCellValue("最低价");
        row.createCell(4).setCellValue("中间价");
        row.createCell(5).setCellValue("最高价");
        row.createCell(6).setCellValue("品种id");
        row.createCell(7).setCellValue("品种名称");

        for (int j = 1; j < total + 1; j++) {
            Row row123 = sheet1.createRow(j);
            row123.createCell(0).setCellValue(data.get(j - 1).getMarketName());
            row123.createCell(1).setCellValue(data.get(j - 1).getProvinceName());
            row123.createCell(2).setCellValue(data.get(j - 1).getAreaName());
            row123.createCell(3).setCellValue(data.get(j - 1).getMinimumPrice());
            row123.createCell(4).setCellValue(data.get(j - 1).getMiddlePrice());
            row123.createCell(5).setCellValue(data.get(j - 1).getHighestPrice());
            row123.createCell(6).setCellValue(data.get(j - 1).getVarietyId());
            row123.createCell(7).setCellValue(data.get(j - 1).getVarietyName());
        }
        try{
            wb.write(new FileOutputStream(filePath));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
