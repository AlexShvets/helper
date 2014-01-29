package utils;


import junit.framework.Assert;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import java.util.ArrayList;

import static utils.ExcelHelper.getExcelSheet;
import static utils.ExcelHelper.getStringValueFromRow;
import static utils.ExcelHelper.readAllFileFromSheet1C;
import static utils.ExcelHelper.readAllFileFromSheetCert;


public class ExcelHelperTest extends UnitTest {
  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testGetExcelSheet() throws Exception {
    Assert.assertTrue(FileUtils.isExistFile("/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls"));
    Assert.assertNotNull(getExcelSheet("/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls"));

  }

  @Test
  public void testGetStringValueFromRow() throws Exception {
    Assert.assertTrue(FileUtils.isExistFile("/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls"));
    HSSFSheet sheet = getExcelSheet("/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls");
    Assert.assertNotNull(sheet);
    String s = getStringValueFromRow(2,sheet);
//    System.out.println("Строка = "+s);

  }

  @Test
  public void testReadAllFileFromSheet() throws Exception {
    Assert.assertTrue(FileUtils.isExistFile("/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls"));
    HSSFSheet sheet = getExcelSheet("/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls");
    Assert.assertNotNull(sheet);
    ArrayList<String> organisations = readAllFileFromSheet1C(sheet);
    organisations.remove(0);
    organisations.remove(organisations.size()-1);
    for (String s:organisations){
//      System.out.println(s);
    }

  }

  @Test
  public void testReadAllFileFromSheetCert() throws Exception {
    Assert.assertTrue(FileUtils.isExistFile("/home/user/srohelper/srohelper/excelFiles/МОП_С.xls"));
    HSSFSheet sheet = getExcelSheet("/home/user/srohelper/srohelper/excelFiles/МОП_С.xls");
    Assert.assertNotNull(sheet);
    ArrayList<String> certs = readAllFileFromSheetCert(sheet);
    certs.remove(0);
    certs.remove(certs.size()-1);
    for (String s:certs){
      System.out.println(s);
    }

  }

}
