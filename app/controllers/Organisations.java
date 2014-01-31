package controllers;

import models.Certificate;
import models.Organisation;
import models.Scope;
import models.Setting;
import models.SettingType;
import models.certificateStatusInSro;
import play.Play;
import play.mvc.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Organisations extends Controller {

  public static void showOrgsSmr(){
    list(Scope.SMR);

  }

  private static void list(Scope scope) {
    List<Organisation> organisations= Organisation.getOrgListByScope(scope);
    renderTemplate("Organisations/list.html", organisations, scope);
  }

  public static void showPr(){
    list(Scope.PR);

  }

  public static void show(int id){
    Organisation organisation = Organisation.findById(id);
    List<Certificate> certificateList = Certificate.find("organisation = ? and certificatestatusinsro > ? order by protocoldate desc", organisation, certificateStatusInSro.NEW.ordinal()).fetch();
    render(organisation, certificateList);

  }

  public static void showSelectedOrgsSmr(String name, String inn, String head) {
    showSelectedOrgs(name, inn, head, Scope.SMR);
  }

  public static void showSelectedOrgsPr(String name, String inn, String head) {
    showSelectedOrgs(name, inn, head, Scope.PR);
  }

  private static void showSelectedOrgs(String name, String inn, String head, Scope scope) {
    List<Organisation> organisations = null;
    organisations = getOrganisations(name, inn, head, organisations, scope);
    if (organisations.size()>0){
      renderTemplate("Organisations/list.html", organisations,scope);
    }

    renderTemplate("Organisations/list.html", organisations);
  }

  private static List<Organisation> getOrganisations(String name, String inn, String head, List<Organisation> organisations, Scope scope) {
    if (name!=null ){
      organisations= Organisation.find("(upper(fullname) like upper(?) or upper(name) like upper(?)) and (scope = ?)  ", "%"+name+"%", "%"+name+"%", scope).fetch();
    }
    if (inn!=null ){
      organisations= Organisation.find("upper(inn) like upper(?)  and scope = ?  ", "%"+inn+"%", scope).fetch();
    }
    if (head!=null ){
      organisations= Organisation.find("upper(head) like upper(?)  and scope = ?  ", "%"+head+"%", scope).fetch();
    }
    return organisations;
  }

  public static int AUTOCOMPLETE_MAX = 10;

  public static void autocompleteByNameSmr(final String term) {
    final List<String> response = Organisation.find("upper(fullname) like upper(?) and scope = ?", "%"+term+"%", Scope.SMR).fetch();
    renderJSON(response);
  }

  public static void autocompleteByNamePr(final String term) {
    final List<String> response = Organisation.find("upper(fullname) like upper(?) and scope = ?", "%"+term+"%", Scope.PR).fetch();
    renderJSON(response);
  }

  public static void update(){
    List<Setting> settingList = Setting.find("settingtype = ?", SettingType.FILES_ORGANISATION_1C).fetch();
    for (Setting setting:settingList){
      try {
        Files.copy(Paths.get(setting.value), Paths.get(Play.applicationPath.getPath()) );
      } catch (IOException e) {
        System.out.println("файлы не скопированы");
      }
    }
  }

  public static void updateExcel(){

  }

}
