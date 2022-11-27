package com.vermeg.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.vermeg.payload.responses.IssueDetails;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Utilities {


    public  static Stream<JsonElement> arrayToStream(JsonArray array) {
        return      StreamSupport.stream( Spliterators.spliteratorUnknownSize(array.iterator(),
                Spliterator.ORDERED),false);
    }

    public  static LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date  = date.split("T")[0];
        return   LocalDate.parse(date , formatter );
    }
    public static ByteArrayInputStream getExcel(List<IssueDetails> issueDetails , String title, String file ) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        String resource = new ClassPathResource("word/Jirapi.xlsx").getPath();
//        InputStream resource1 = new ClassPathResource("word/Jirapi.xlsx").getInputStream();


        //File filex = new File("Jirapi.xlsx");

//        System.out.println(resource);
//        System.out.println(resource1);
        Resource resource = new ClassPathResource("/word/Jirapi.xlsx");

        InputStream inputStream = resource.getInputStream();
       // System.out.println(filex);
     //   FileInputStream inputStream = new FileInputStream(filex);

        Workbook workbook = WorkbookFactory.create(inputStream);

            //XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            IssueDetails[] bookData = new IssueDetails[issueDetails.size()] ;
            issueDetails.toArray(bookData) ;


            int rowCount = sheet.getFirstRowNum()+1;
            Row rowTitle = sheet.createRow(0);
            Cell cellTitle= rowTitle.createCell(0);
            CellStyle cellStyleTitle= workbook.createCellStyle();
            cellStyleTitle.setWrapText(true);
            cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
            cellTitle.setCellStyle(cellStyleTitle);
            cellTitle.setCellValue(title);


            for (IssueDetails field : bookData) {
                Row  row = sheet.createRow(++rowCount);
                int  columnCount = 0;
                for(String obj : field.getString().split(",")){
                    Cell cell = row.createCell(columnCount++);
                    cell.setCellValue(obj);
                }



            }
            inputStream.close();
            workbook.write(out);



        return new ByteArrayInputStream(out.toByteArray());

    }


}
