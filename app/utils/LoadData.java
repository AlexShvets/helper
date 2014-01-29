package utils;

import models.Certificate;
import models.Organisation;
import models.Scope;
import models.objectStatusofLoad;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import static utils.ExcelHelper.getExcelSheet;
import static utils.ExcelHelper.readAllFileFromSheet1C;
import static utils.ExcelHelper.readAllFileFromSheetCert;
import static utils.ExcelHelper.readAllFileFromSheetCertExel;
import static utils.ExcelHelper.readAllFileFromSheetExcel;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 1/15/14
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadData {

  public static boolean loadOrganisationsFrom1C(Scope scope, String fileNameFrom1C){

    if (!FileUtils.isExistFile(fileNameFrom1C)){
      System.out.println("Файл реестра 1C не найден");
      return false;
    };
    HSSFSheet sheet = getExcelSheet(fileNameFrom1C);
    ArrayList<String> organisations = readAllFileFromSheet1C(sheet);
    organisations.remove(0);
    organisations.remove(organisations.size() - 1);
    Organisation.deleteOnLoadStatusOrg(objectStatusofLoad.LOADED, scope);
    System.out.println("Начинаем загрузку организации для сферы "+scope);
    for (String organisationData: organisations){
      getDataForOrganisationFrom1C(scope, organisationData.split("///"));
    }
//    for(String s:organisations.get(5).split("///"))
//      System.out.println(s);
//    getDataForOrganisationFrom1C(scope, organisations.get(5).split("///"));
    return true;
  }

  private static void getDataForOrganisationFrom1C(Scope scope, String[] organisationData) {
    Organisation organisationNew = new Organisation(organisationData[0],
        organisationData[1].trim(),
        organisationData[2].trim(),
        organisationData[3].trim(),
        organisationData[4].trim(),
        organisationData[5].trim(),
        organisationData[6].trim(),
        organisationData[7].trim(),
        organisationData[8].trim(),
        organisationData[9].trim(),
        organisationData[10].trim(),
        organisationData[11].trim(),
        organisationData[12].trim(),
        organisationData[13].trim(),
        organisationData[14].trim(),
        organisationData[15].trim(),
        scope);
    Organisation organisationExist = Organisation.find("inn = ? and scope = ?", organisationNew.inn, organisationNew.scope).first();
    if (organisationExist==null){
      System.out.println("Не найдена эквивалентная организация");
      organisationNew.statusofLoad = objectStatusofLoad.INBASE;
      organisationNew.save();
    } else {
      if (!organisationNew.equals1cData(organisationExist)){
        System.out.println("Найдена эквивалентная организация "+organisationExist.name);
        organisationExist.hasChange = true;
        organisationExist.save();
        organisationNew.statusofLoad = objectStatusofLoad.LOADED;
        organisationNew.save();
      }
    }
  }

  public static boolean loadOrganisationsFromExcel(Scope scope,  String fileNameFromExcel){
    if (!FileUtils.isExistFile(fileNameFromExcel)){
      System.out.println("Файл реестра Excel не найден");
      return false;
    };
    HSSFSheet sheet = getExcelSheet(fileNameFromExcel);
    ArrayList<String> organisations = readAllFileFromSheetExcel(sheet);
    organisations.remove(0);
    for (String organisationData: organisations){
//      System.out.println(organisationData);
    }
    for (String organisationData : organisations) {
      String[] orgDataToInsert = organisationData.split("///");
      Organisation org = Organisation.find("inn = ? and scope = ? and statusofload = ?", orgDataToInsert[1].trim(), scope, objectStatusofLoad.INBASE.ordinal()).first();
      if (org != null) {
        org.filialNumber = Integer.parseInt(orgDataToInsert[0].trim());
        if ((!orgDataToInsert[2].contains(".")) && (!orgDataToInsert[2].contains("-"))) {
          org.powerOfAttorneyIsValidUntil = null;
          System.out.println("Дата доверенности не укакзана для "+orgDataToInsert[2]+"  orgDataToInsert[2].split(\".\").length = " +orgDataToInsert[2].split(".").length);

        } else {
          DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
          if (orgDataToInsert[2].indexOf("-") > 0) {
            formatter = DateTimeFormat.forPattern("dd-MMM-YYYY");
          }
          org.powerOfAttorneyIsValidUntil = formatter.parseDateTime(orgDataToInsert[2].trim()).toDate();
        }
        org.save();

      }
    }
    return true;
  }

  public static boolean loadCertificatesFrom1C(Scope scope, String fileNameFrom1C){
    if (Organisation.count("scope = ?", scope)<1){
      System.out.println("В базе нет не одной организации для данной сферы деятельности ( "+scope+" )");
      return false;
    }
    if (!FileUtils.isExistFile(fileNameFrom1C)){
      System.out.println("Файл реестра 1C не найден");
      return false;
    };
    Certificate.deleteOnLoadStatusCert(objectStatusofLoad.LOADED, scope);
    HSSFSheet sheet = getExcelSheet(fileNameFrom1C);
    ArrayList<String> certs = readAllFileFromSheetCert(sheet);
    certs.remove(0);
    certs.remove(certs.size() - 1);
    for (String certData: certs){
      getCertDataFrom1C(scope, certData.split("///"));
    }
    return true;
  }

  private static void getCertDataFrom1C(Scope scope, String[] certData) {
    Certificate certificateNew = new Certificate(
        certData[1].trim(),
        certData[4].trim(),
        certData[3].trim(),
        certData[0].trim(),
        objectStatusofLoad.LOADED,
        certData[5].trim(),
        certData[2].trim(),
        scope);
    Certificate certificate1Exist = Certificate.find("name = ?", certificateNew.name.trim()).first();
    System.out.println("certificate1Exist "+certificate1Exist);
    if (certificate1Exist==null){
      System.out.println("Не найдено эквивалентное свидетельство");
      certificateNew.statusofLoad = objectStatusofLoad.INBASE;
      certificateNew.save();
    } else {
      if (!certificateNew.equals1cData(certificate1Exist)){
        System.out.println("Найдено эквивалентное свидетельство "+certificate1Exist.name);
        certificate1Exist.hasChange = true;
        certificate1Exist.save();
        certificateNew.statusofLoad = objectStatusofLoad.LOADED;
        certificateNew.save();
      }
    }
    certificateNew.save();
  }



  public static boolean loadCertFromExcel(Scope scope,  String fileNameFromExcel){
    if (!FileUtils.isExistFile(fileNameFromExcel)){
      System.out.println("Файл реестра Excel не найден");
      return false;
    };
    HSSFSheet sheet = getExcelSheet(fileNameFromExcel);
    ArrayList<String> certs = readAllFileFromSheetCertExel(sheet);
    certs.remove(0);
    for (String certsData: certs){
//      System.out.println(certsData);
    }
    for (String certData : certs) {
      String[] certDataToInsert = certData.split("///");
      Certificate cert = Certificate.find("name = ? and statusofload = ?", certDataToInsert[0].trim(), objectStatusofLoad.INBASE.ordinal()).first();
      if ((cert != null) && (certDataToInsert.length==2))  {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        if (certDataToInsert[1].indexOf(".") > 0) {
          formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
          cert.dateOfDispatchDocuments = formatter.parseDateTime(certDataToInsert[1].trim().replaceAll(" ", "")).toDate();

        }else {
          cert.dateOfDispatchDocuments = DateTime.now().toDate();
        }
        cert.save();

      }
    }
    return true;
  }

}
