package models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.db.jpa.GenericModel;
import utils.ExcelHelper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@SequenceGenerator(name = "organisation_sequence", sequenceName = "organisation_sequence")
public class Organisation extends GenericModel{

  public static final String EMPTYSTRING = "Не задан";
  @Id
  @GeneratedValue(generator = "organisation_sequence")
  int id;

  @Column(nullable = true)
  public String name;

  @Column(nullable = true)
  public String shortName;

  @Column(nullable = true)
  public String inn;

  @Column(nullable = true)
  public String opf;

  @Column(nullable = true)
  public String telephoneCod;

  @Column(nullable = true)
  public String telephoneNumber;

  @Column(nullable = true)
  public String ogrn;

  @Column(nullable = true)
  public String fax;

  @Column(nullable = true)
  public String factAdress;

  @Column(nullable = true)
  public String lawAdress;

  @Column(nullable = true)
  public String email;

  @Column(nullable = true)
  public String shortNameOfficial;

  @Column(nullable = true)
  public String postAdress;

  @Column(nullable = true)
  public String fullName;

  @Column(nullable = true)
  public String head;


  @Column(nullable = true)
  public DateTime removalDate = null;

  @Column(nullable = true)
  public Date firstCertificateDate;

  @Column (nullable = true)
  public Date powerOfAttorneyIsValidUntil;


  @Column
  public objectStatusofLoad statusofLoad;

  @Column
  public OrganisationStatusInSro organisationStatusInSro;

  @Column(nullable = true)
  public String registrNumber = EMPTYSTRING;

  @Override
  public String toString() {
    return "Organisation{" +
        "filialNumber=" + filialNumber +
        ", fullName='" + fullName + '\'' +
        ", inn='" + inn + '\'' +
        '}';
  }

  @Column(nullable = true)
  public int filialNumber ;

  @Column
  public Scope scope;

  @Column
  public boolean hasChange = false;

  public Organisation(String name, String inn, String fullName, String telephoneCod, String telephoneNumber,
                      String ogrn, String opf, String fax, String factAdress, String email, String lawAdress,
                      String shortName, String postAdress, String head, String firstCertificateDate,
                      String organisationStatusInSro, Scope scope) {
    this.opf = opf;
    this.email = email;
    this.factAdress = factAdress;
    this.fax = fax;
    if (firstCertificateDate.contains(ExcelHelper.Empty_Data)){
      this.firstCertificateDate = null;

    }else {
      DateTimeFormatter formatter = DateTimeFormat.forPattern( "dd.MM.YYYY H:mm:ss");
      this.firstCertificateDate = formatter.parseDateTime(firstCertificateDate).toDate();
    }
    this.fullName = fullName;
    this.head = head;
    this.inn = inn;
    this.lawAdress = lawAdress;
    this.name = name;
    this.ogrn = ogrn;
    this.postAdress = postAdress;
    this.scope = scope;
    this.shortName = shortName;
    this.statusofLoad = objectStatusofLoad.LOADED;
    this.telephoneCod = telephoneCod;
    this.telephoneNumber = telephoneNumber;
    this.organisationStatusInSro = OrganisationStatusInSro.getStatusFromString(organisationStatusInSro);
    this.scope = scope;
  }


  public boolean equals1cData(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Organisation that = (Organisation) o;

    if (email != null ? !email.equals(that.email) : that.email != null) return false;
    if (factAdress != null ? !factAdress.equals(that.factAdress) : that.factAdress != null) return false;
    if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
    if (firstCertificateDate != null ? !firstCertificateDate.equals(that.firstCertificateDate) : that.firstCertificateDate != null) return false;
    if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
    if (head != null ? !head.equals(that.head) : that.head != null) return false;
    if (inn != null ? !inn.equals(that.inn) : that.inn != null) return false;
    if (lawAdress != null ? !lawAdress.equals(that.lawAdress) : that.lawAdress != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (ogrn != null ? !ogrn.equals(that.ogrn) : that.ogrn != null) return false;
    if (opf != null ? !opf.equals(that.opf) : that.opf != null) return false;
    if (organisationStatusInSro != that.organisationStatusInSro) return false;
    if (postAdress != null ? !postAdress.equals(that.postAdress) : that.postAdress != null) return false;
    if (scope != that.scope) return false;
    if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
    if (shortNameOfficial != null ? !shortNameOfficial.equals(that.shortNameOfficial) : that.shortNameOfficial != null) return false;
    if (telephoneCod != null ? !telephoneCod.equals(that.telephoneCod) : that.telephoneCod != null) return false;
    if (telephoneNumber != null ? !telephoneNumber.equals(that.telephoneNumber) : that.telephoneNumber != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
    result = 31 * result + (inn != null ? inn.hashCode() : 0);
    result = 31 * result + (opf != null ? opf.hashCode() : 0);
    result = 31 * result + (telephoneCod != null ? telephoneCod.hashCode() : 0);
    result = 31 * result + (telephoneNumber != null ? telephoneNumber.hashCode() : 0);
    result = 31 * result + (ogrn != null ? ogrn.hashCode() : 0);
    result = 31 * result + (fax != null ? fax.hashCode() : 0);
    result = 31 * result + (factAdress != null ? factAdress.hashCode() : 0);
    result = 31 * result + (lawAdress != null ? lawAdress.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (shortNameOfficial != null ? shortNameOfficial.hashCode() : 0);
    result = 31 * result + (postAdress != null ? postAdress.hashCode() : 0);
    result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
    result = 31 * result + (head != null ? head.hashCode() : 0);
    result = 31 * result + (removalDate != null ? removalDate.hashCode() : 0);
    result = 31 * result + (firstCertificateDate != null ? firstCertificateDate.hashCode() : 0);
    result = 31 * result + (powerOfAttorneyIsValidUntil != null ? powerOfAttorneyIsValidUntil.hashCode() : 0);
    result = 31 * result + (organisationStatusInSro != null ? organisationStatusInSro.hashCode() : 0);
    result = 31 * result + (registrNumber != null ? registrNumber.hashCode() : 0);
    result = 31 * result + filialNumber;
    result = 31 * result + (scope != null ? scope.hashCode() : 0);
    return result;
  }
  public static int deleteOnLoadStatusOrg(objectStatusofLoad objectStatusofLoad,  Scope scope){
    return  Organisation.delete("statusofload = ? and scope = ?", objectStatusofLoad.ordinal(),scope );
  }

  public static List<Organisation> getOrgListByScope(Scope scope){
    return Organisation.find("scope = ? and (organisationStatusInSro = ? or organisationStatusInSro = ?) and statusofLoad = ?  order by filialnumber", scope, OrganisationStatusInSro.MEMBER, OrganisationStatusInSro.DISSMISSED, objectStatusofLoad.INBASE).fetch();

  }
  public boolean isDissmissed(){
    return this.organisationStatusInSro==OrganisationStatusInSro.DISSMISSED;
  }

  public boolean isSmr(){
    return this.scope==Scope.SMR;
  }

  public boolean isPr(){
    return this.scope==Scope.PR;
  }
}

