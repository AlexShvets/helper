package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;
import utils.BdHelper;
import utils.LoadData;


public class OrganisationTest extends UnitTest {
  @Before
  public void setUp() throws Exception {
    BdHelper.prepareDb();

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testLoadOrg() throws Exception {
    LoadData.loadOrganisationsFrom1C(Scope.SMR, "/home/user/srohelper/srohelper/excelFiles/МОС_К .xls");
    LoadData.loadOrganisationsFromExcel(Scope.SMR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls");
    LoadData.loadOrganisationsFrom1C(Scope.PR, "/home/user/srohelper/srohelper/excelFiles/МОП_К .xls");
    LoadData.loadOrganisationsFromExcel(Scope.PR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОП.xls");

  }
}
