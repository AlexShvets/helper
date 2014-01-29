package utils;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelHelper {

  public static HSSFSheet getExcelSheet(String fileName) {
    HSSFSheet sheet;
    try {
      FileInputStream file = new FileInputStream(new File(fileName));
      HSSFWorkbook workbook = new HSSFWorkbook(file);
      sheet = workbook.getSheetAt(0);
    } catch (IOException e) {
//      System.out.println();
      return null;
    }
    return sheet;
  }

  public static String getStringValueFromRow(int rowIndex, HSSFSheet sheet){
    if (sheet==null) return null;
    StringBuilder result = new StringBuilder();
    Row row = sheet.getRow(rowIndex);
    rowToStringFrom1COrg(result, row, 1, 16);
    return result.toString();
  }

  private static void rowToStringFrom1COrg(StringBuilder result, Row row, int indexBegin, int indexEnd) {

    for (int i = indexBegin; i < indexEnd + 1; i++) {
      Cell cell = row.getCell(i);
      String temp;
      if (cell == null) {
        temp = "не задано";
      } else {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

          cell.setCellType(Cell.CELL_TYPE_STRING);

          temp = cell.toString();

        } else {
          temp = cell.toString();
        }

      }
      temp = temp.replaceAll("\n", " ");
      while (temp.indexOf("   ")>0){
        temp=temp.replaceAll("   ", " ");
      }
      result.append(temp);
      result.append("///");
    }
  }

  private static void rowToStringFromExcel(StringBuilder result, Row row ) {
    Cell cell = row.getCell(0);
    if ((cell.getCellType() == HSSFCell.CELL_TYPE_STRING) && (!cell.getStringCellValue().contains("П") ) ||(cell.getCellType()==Cell.CELL_TYPE_NUMERIC) ) {
      setStringTypeToCell(cell);
      result.append(cell.toString()).append("///");
      cell = row.getCell(3);
      setStringTypeToCell(cell);
      result.append(cell.toString()).append("///");
      cell = row.getCell(12);
      if (cell == null) {
        result.append("не задано").append("///");
      } else {
        result.append(cell.toString()).append("///");
      }
    } else {
//      System.out.println("Формат ячейки = " +cell.getCellType() + " А тип строки = " +Cell.CELL_TYPE_STRING+ " Значение = "+ cell.toString()+" Numeric = "+Cell.CELL_TYPE_NUMERIC);
    }
  }


  private static void rowToStringCertFromExcel(StringBuilder result, Row row ) {
    Cell cellName= row.getCell(9);
    if (cellName==null) return;
    setStringTypeToCell(cellName);
    result.append(cellName.toString().trim()).append("///");
    Cell cellDate= row.getCell(8);

    if ((cellDate!=null)){
        setStringTypeToCell(cellDate);
        result.append(cellDate.toString().trim()).append("///");
    } else {
      result.append(DateTime.now().toDate().toString().trim()).append("///");
    }

  }

  private static void setStringTypeToCell(Cell cell) {
    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
      cell.setCellType(Cell.CELL_TYPE_STRING);
    }
  }

  public static ArrayList<String> readAllFileFromSheet1C(HSSFSheet sheet){
    ArrayList<String> strings = new ArrayList<>();
    if (sheet==null) return null;
    StringBuilder result;
    for (Row row :sheet){
      result = new StringBuilder();
      if ((row.getCell(1)!=null) && (row.getCell(2)!=null)){
        rowToStringFrom1COrg(result, row, 1, 16);
        if (result!=null){
          strings.add(result.toString());
        }
      }

    }
    return strings;
  }

  public static ArrayList<String> readAllFileFromSheetExcel(HSSFSheet sheet){
    ArrayList<String> strings = new ArrayList<>();
    if (sheet==null) return null;
    StringBuilder result;
    for (Row row :sheet){
      result = new StringBuilder();
      rowToStringFromExcel(result, row);
      if (!result.toString().isEmpty()){
        strings.add(result.toString());
      }
    }
    return strings;
  }

  public static ArrayList<String> readAllFileFromSheetCert(HSSFSheet sheet){
    ArrayList<String> strings = new ArrayList<>();
    if (sheet==null) return null;
    StringBuilder result;
    for (Row row :sheet){
      result = new StringBuilder();
      rowToStringFrom1cCert(result, row);
      if (!result.toString().isEmpty()){
        strings.add(result.toString());
      }
    }
    return strings;
  }


  public static ArrayList<String> readAllFileFromSheetCertExel(HSSFSheet sheet){
    ArrayList<String> strings = new ArrayList<>();
    if (sheet==null) return null;
    StringBuilder result;
    for (Row row :sheet){
      result = new StringBuilder();
      rowToStringCertFromExcel(result, row);
      if (!result.toString().isEmpty()){
        strings.add(result.toString());
      }
    }
    return strings;
  }

  private static void rowToStringFrom1cCert(StringBuilder result, Row row ) {
    Cell cell = row.getCell(1);
    if (cell==null) return;
    addValue(result, cell);
    cell = row.getCell(2);
    if (cell!=null){
      addValue(result, cell);
    }
    cell = row.getCell(4);
    addValueOrString(result, cell);
    cell = row.getCell(7);
    addNumericValue(result, cell);
    cell = row.getCell(8);
    addNumericValue(result, cell);
    cell = row.getCell(6);
    addValueOrString(result, cell);
  }

  private static void addNumericValue(StringBuilder result, Cell cell) {
    if (cell!=null){
      setStringTypeToCell(cell);
      result.append(cell.toString().trim());
    } else {
      result.append("не задано");
    }
    result.append("///");
  }

  private static void addValueOrString(StringBuilder result, Cell cell) {
    if (cell!=null){
      result.append(cell.toString().trim());
    }else {
      result.append("не задано");
    }
    result.append("///");
  }

  private static void addValue(StringBuilder result, Cell cell) {
    result.append(cell.toString().trim());
    result.append("///");
  }

}
