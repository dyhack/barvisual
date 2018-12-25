package cn.dyhack.barvisual.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
/**
 * 
 * @className ExportExcel
 * @deprecated List集合导出成Excel表格工具类
 * @author pengfei.xiong
 * @date 2017年10月9日
 */
public final class ExportExcel {
    /***
       * 构造方法
       */
      private ExportExcel() {

      }

      /***
       * 工作簿
       */
      private static HSSFWorkbook workbook;

      /***
       * sheet
       */
      private static HSSFSheet[] sheet ;
     /***
       * 标题行开始位置
       */
      private static final int TITLE_START_POSITION = 0;

      /***
       * 时间行开始位置
       */
      private static final int DATEHEAD_START_POSITION = 1;

      /***
       * 表头行开始位置
       */
      private static final int HEAD_START_POSITION = 2;

      /***
       * 文本行开始位置
       */
      private static final int CONTENT_START_POSITION = 3;


      /**
       * 
       * @param dataList
       *        对象集合
       * @param titleMap
       *        表头信息（对象属性名称->要显示的标题值)[按顺序添加]
       * @param sheetName
       *        sheet名称和表头值
       */
        // 初始化workbook
      public static HSSFWorkbook excelExport(int index,List<?> dataList, Map<String, String> titleMap, String []sheetName) {
        
        //initHSSFWorkbook(sheetName);
        // 标题行
        createTitleRow(index,titleMap, sheetName);
        // 时间行
        createDateHeadRow(index,titleMap);
        // 表头行
        createHeadRow(index,titleMap);
        // 文本行
        createContentRow(index,dataList, titleMap);
        //设置自动伸缩
        //autoSizeColumn(titleMap.size());
        // 写入处理结果
        try {
          return workbook;
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        return null;
      }

      /***
       * 
       * @param sheetName
       *        sheetName
       */
      public static void initHSSFWorkbook(String []sheetName) {
        workbook = new HSSFWorkbook();
        HSSFSheet[] tempSheet = new HSSFSheet[sheetName.length];
        sheet =tempSheet;
        for(int i=0;i<sheetName.length;i++)
        sheet[i] = workbook.createSheet(sheetName[i]);
      }

      /**
       * 生成标题（第零行创建）
       * @param titleMap 对象属性名称->表头显示名称
       * @param sheetName sheet名称
       */
      private static void createTitleRow(int index,Map<String, String> titleMap, String sheetName[]) {
        
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, titleMap.size() - 1);
        sheet[index].addMergedRegion(titleRange);
        HSSFRow titleRow = sheet[index].createRow(TITLE_START_POSITION);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(sheetName[index]);
      }

      /**
       * 创建时间行（第一行创建）
       * @param titleMap 对象属性名称->表头显示名称
       */
      private static void createDateHeadRow(int index,Map<String, String> titleMap) {
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, titleMap.size() - 1);
        sheet[index].addMergedRegion(dateRange);
        HSSFRow dateRow = sheet[index].createRow(DATEHEAD_START_POSITION);
        HSSFCell dateCell = dateRow.createCell(0);   
        dateCell.setCellValue(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
      }

      /**
       * 创建表头行（第二行创建）
       * @param titleMap 对象属性名称->表头显示名称
       */
      private static void createHeadRow(int index,Map<String, String> titleMap) {
        // 第1行创建
        HSSFRow headRow = sheet[index].createRow(HEAD_START_POSITION);
        int i = 0;
        for (String entry : titleMap.keySet()) {
          HSSFCell headCell = headRow.createCell(i);
          headCell.setCellValue(titleMap.get(entry));
          i++;
        }
      }

     /**
      * 
      * @param dataList 对象数据集合
      * @param titleMap 表头信息
      */
      private static void createContentRow(int index,List<?> dataList, Map<String, String> titleMap) {
        try {
          int i=0;
          for (Object obj : dataList) {
            HSSFRow textRow = sheet[index].createRow(CONTENT_START_POSITION + i);
            int j = 0;
            for (String entry : titleMap.keySet()) {
              String method = "get" + entry.substring(0, 1).toUpperCase() + entry.substring(1);
              Method m = obj.getClass().getMethod(method, null);
              String value =   m.invoke(obj, null).toString();
              HSSFCell textcell = textRow.createCell(j);
              textcell.setCellValue(value);
              j++;
            }
            i++;
          }

        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      /**
       * 自动伸缩列（如非必要，请勿打开此方法，耗内存）
       * @param size 列数
       */
      private static void autoSizeColumn(int index,Integer size) { 
        for (int j = 0; j < size; j++) {
          sheet[index].autoSizeColumn(j);
        }
      }
      
      
}

