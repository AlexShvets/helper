package utils;

import models.Organisation;
import models.OrganisationStatusInSro;
import models.Scope;
import play.db.jpa.JPA;


public class BdHelper {

  private final static boolean USE_TRUNCATE = false;




  public static void dropDb() {
    JPA.em().createNativeQuery("DELETE FROM certificate").executeUpdate();
    JPA.em().createNativeQuery("DELETE FROM organisation").executeUpdate();
    commitAndBegin();

  }


  public static void commit() {
    if (JPA.em().getTransaction().isActive()) {
      JPA.em().flush();
      JPA.em().getTransaction().commit();
    }
  }


  public static void rollback() {
    if (JPA.em().getTransaction().isActive()) {
      JPA.em().clear();
      JPA.em().getTransaction().rollback();
    }
  }

  public static void commitAndBegin() {
    commit();
    beginTx();
  }

  public static void rollbackAndBegin() {
    rollback();
    beginTx();
  }

  public static void beginTx() {
    if (!JPA.em().getTransaction().isActive()) {
      JPA.em().getTransaction().begin();
    }
  }

  public static void prepareDb() {
    //rollbackAndBegin();
    dropDb();


  }

  public static void fillBd() {
    LoadData.loadOrganisationsFrom1C(Scope.SMR, "/home/user/srohelper/srohelper/excelFiles/МОС_К .xls");
    LoadData.loadOrganisationsFromExcel(Scope.SMR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls");
    LoadData.loadOrganisationsFrom1C(Scope.PR, "/home/user/srohelper/srohelper/excelFiles/МОП_К .xls");
    LoadData.loadOrganisationsFromExcel(Scope.PR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОП.xls");

    LoadData.loadCertificatesFrom1C(Scope.SMR,"/home/user/srohelper/srohelper/excelFiles/МОС_С.xls" );
    LoadData.loadCertificatesFrom1C(Scope.PR,"/home/user/srohelper/srohelper/excelFiles/МОП_С.xls" );

    LoadData.loadCertFromExcel(Scope.PR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОП.xls");
    LoadData.loadCertFromExcel(Scope.SMR,  "/home/user/srohelper/srohelper/excelFiles/РЕЕСТР_МОС.xls");

    long countSmr = Organisation.count("scope = ? and organisationStatusInSro = ?", Scope.SMR, OrganisationStatusInSro.MEMBER);
    long countPR = Organisation.count("scope = ? and organisationStatusInSro = ?",Scope.PR, OrganisationStatusInSro.MEMBER );
    System.out.println("Действующих в строительстве = " + countSmr);
    System.out.println("Действующих в проектировании = " + countPR);
  }


}
