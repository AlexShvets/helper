package controllers;

import models.Certificate;
import models.Organisation;
import models.Scope;
import models.certificateStatusInSro;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;


public class Organisations extends Controller {

  public static void showOrgsSmr(){
    showOrgs(Scope.SMR);

  }

  private static void showOrgs(Scope scope) {
    List<Organisation> organisations= Organisation.getOrgListByScope(scope);
    renderTemplate("Organisations/showOrgs.html", organisations, scope);
  }

  public static void showPr(){
    showOrgs(Scope.PR);

  }

  public static void show(int id){
    Organisation organisation = Organisation.findById(id);
    List<Certificate> certificateList = Certificate.find("organisation = ? and certificatestatusinsro > ? order by protocoldate desc", organisation, certificateStatusInSro.NEW.ordinal()).fetch();
    render(organisation, certificateList);

  }

  public static void showSelectedOrgsSmr(String name, String inn, String head) {
    List<Organisation> organisations = null;
    organisations = getOrganisations(name, inn, head, organisations, Scope.SMR);
    if (organisations.size()>0){
        Scope scope = organisations.get(0).scope;
        renderTemplate("Organisations/showOrgs.html", organisations, scope);
      }

    renderTemplate("Organisations/showOrgs.html", organisations);
  }

  public static void showSelectedOrgsPr(String name, String inn, String head) {
    List<Organisation> organisations = null;
    organisations = getOrganisations(name, inn, head, organisations, Scope.PR);
    if (organisations.size()>0){
      Scope scope = organisations.get(0).scope;
      renderTemplate("Organisations/showOrgs.html", organisations, scope);
    }

    renderTemplate("Organisations/showOrgs.html", organisations);
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

}
