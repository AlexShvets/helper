package utils;

import models.Organisation;
import models.OrganisationStatusInSro;
import models.Scope;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 1/17/14
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class TemplateHelperTest extends UnitTest {
  @Before
  public void setUp() throws Exception {
    BdHelper.prepareDb();

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testCreateSimpleTemplate() throws Exception {
    fillBd();

    List<Object> organisationArrayList = Organisation.find("organisationStatusInSro = ? and scope = ?", OrganisationStatusInSro.MEMBER, Scope.SMR).fetch();
    for (Object organisation : organisationArrayList) {
      Organisation temp = (Organisation) organisation;
      TemplateHelper templateHelper = new TemplateHelper();
      WordprocessingMLPackage template = null;
      template = templateHelper.getTemplate("/home/user/srohelper/srohelper/templateFiles/uv-mos.docx");

      if (temp.scope == Scope.SMR) {
        template = templateHelper.getTemplate("/home/user/srohelper/srohelper/templateFiles/uv-mos.docx");
      }
      templateHelper.replacePlaceholder(template, temp.inn, "inn");
      templateHelper.replacePlaceholder(template, temp.opf+" "+temp.shortName, "fullName1");
      templateHelper.replacePlaceholder(template, temp.fullName, "fullName");
      templateHelper.replacePlaceholder(template, temp.opf, "Opf");
      templateHelper.replacePlaceholder(template, temp.shortName, "shortName");
      templateHelper.writeDocxToStream(template, "/home/user/srohelper/srohelper/resultTemplateFiles/" + temp.shortName+"_"+temp.inn + ".docx");

    }
  }


  private void fillBd() {
    LoadData.loadOrganisationsFrom1C(Scope.SMR, "/home/user/srohelper/srohelper/excelFiles/МОС_К .xls");
    LoadData.loadOrganisationsFromExcel(Scope.SMR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls");
    LoadData.loadOrganisationsFrom1C(Scope.PR, "/home/user/srohelper/srohelper/excelFiles/МОП_К .xls");
    LoadData.loadOrganisationsFromExcel(Scope.PR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОП.xls");

    LoadData.loadCertificatesFrom1C(Scope.SMR,"/home/user/srohelper/srohelper/excelFiles/МОС_С.xls" );
    LoadData.loadCertificatesFrom1C(Scope.PR,"/home/user/srohelper/srohelper/excelFiles/МОП_С.xls" );

    LoadData.loadCertFromExcel(Scope.PR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОП.xls");
    LoadData.loadCertFromExcel(Scope.SMR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls");

    long countSmr = Organisation.count("scope = ? and organisationStatusInSro = ?",Scope.SMR, OrganisationStatusInSro.MEMBER );
    long countPR = Organisation.count("scope = ? and organisationStatusInSro = ?",Scope.PR, OrganisationStatusInSro.MEMBER );
    System.out.println("Действующих в строительстве = " + countSmr);
    System.out.println("Действующих в проектировании = " + countPR);
  }
}
