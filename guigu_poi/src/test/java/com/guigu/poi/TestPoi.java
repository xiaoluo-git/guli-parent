package com.guigu.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestPoi {

    @Test
    public void testHSSF03() throws IOException {
        Workbook workbook = new HSSFWorkbook();

        Sheet excel = workbook.createSheet("测试03Excel");

        Row row1 = excel.createRow(0);
        Cell cell = row1.createCell(0);
        cell.setCellValue("姓名");
        Cell cell1 = row1.createCell(1);
        cell1.setCellValue("小明");

        FileOutputStream fileOutputStream = new FileOutputStream("D:/test.xls");

        workbook.write(fileOutputStream);

        fileOutputStream.close();
    }

    @Test
    public void testHssf07() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("测试07");
        Row row = sheet.createRow(0);
        Cell  cell1 = row.createCell(0);
        cell1.setCellValue("年龄");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue(12);

        workbook.write(new FileOutputStream("D:/test07.xlsx"));
    }

    @Test
    public void test() throws IOException {
        long start = System.currentTimeMillis();

        Workbook workbook = new HSSFWorkbook();
        Sheet excel = workbook.createSheet();
        for (int i = 0; i < 65536; i++) {

            Row row = excel.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue("姓名");
            Cell cell1 = row.createCell(1);
            cell1.setCellValue("小明" + i);
        }

        FileOutputStream fileOutputStream = new FileOutputStream("D:/test.xls");

        workbook.write(fileOutputStream);
        long end = System.currentTimeMillis();

        System.out.println((end-start) * 1.0/1000);//3.694
        fileOutputStream.close();
    }

    @Test
    public void test01() throws IOException {
        long start = System.currentTimeMillis();

        Workbook workbook = new XSSFWorkbook();
        Sheet excel = workbook.createSheet();
        for (int i = 0; i < 65536; i++) {

            Row row = excel.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue("姓名");
            Cell cell1 = row.createCell(1);
            cell1.setCellValue("小明" + i);
        }

        FileOutputStream fileOutputStream = new FileOutputStream("D:/test.xlsx");

        workbook.write(fileOutputStream);
        long end = System.currentTimeMillis();

        System.out.println((end-start) * 1.0/1000);//15.443
        fileOutputStream.close();
    }

    @Test
    public void test02() throws IOException {
        long start = System.currentTimeMillis();

        Workbook workbook = new SXSSFWorkbook();
        Sheet excel = workbook.createSheet();
        for (int i = 0; i < 5000; i++) {

            Row row = excel.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue("姓名");
            Cell cell1 = row.createCell(1);
            cell1.setCellValue("小明" + i);
        }

        FileOutputStream fileOutputStream = new FileOutputStream("D:/test.xlsx");

        workbook.write(fileOutputStream);
        long end = System.currentTimeMillis();

        System.out.println((end-start) * 1.0/1000);//3.381
        fileOutputStream.close();
    }

    @Test
    public void test04() throws IOException {
        FileInputStream fis = new FileInputStream("D:/test.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        int num = sheet.getLastRowNum();
        System.out.println(num);
        FileOutputStream fos = new FileOutputStream("D:/copy.txt");
        for (int i = 0; i < num; i++) {
            Row row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                fos.write(("第"+(i+1) +"行，第"+ j +"列值：" + cell ).getBytes());
            }
            fos.write("\r\n".getBytes());
        }


    }
}
