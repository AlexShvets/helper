package models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.db.jpa.GenericModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.Date;


@Entity
@SequenceGenerator(name = "cert_sequence", sequenceName = "cert_sequence")
public class Certificate extends GenericModel {
  @Id
  @GeneratedValue(generator = "cert_sequence")
  int id;

  @Column(nullable = true)
  public String name;

  @Column(nullable = true)
  public String state;

  @Column(nullable = true)
  public Date protocolDate;

  @Column(nullable = true)
  public int countTypesOfWork;

  @Column(nullable = true)
  public int countGroupsOfWork;

  @Column(nullable = true)
  public boolean hasChange;



  @Column(nullable = true)
  public String sumOfGeneralContractor;

  @Column(nullable = true)
  public certificateStatusInSro certificateStatusInSro;

  @Column(nullable = true)
  public objectStatusofLoad statusofLoad;

  @Column(nullable = true)
  public Date dateOfDispatchDocuments;



  @ManyToOne
  public Organisation organisation;

  @Override
  public String toString() {
    return "Certificate{" +
        "state='" + state + '\'' +
        ", countGroupsOfWork=" + countGroupsOfWork +
        ", countTypesOfWork=" + countTypesOfWork +
        ", name='" + name + '\'' +
        ", organisation=" + organisation +
        ", sumOfGeneralContractor='" + sumOfGeneralContractor + '\'' +
        '}';
  }

  public Certificate(String certificateStatusInSro, String countGroupsOfWork,
                     String countTypesOfWork, String name,
                     objectStatusofLoad statusofLoad, String sumOfGeneralContractor, String protocolDate, Scope scope) {

    this.certificateStatusInSro = models.certificateStatusInSro.getStatusFromString(certificateStatusInSro);
    if (countGroupsOfWork.contains("не")){
      this.countGroupsOfWork=0;
    } else {
      this.countGroupsOfWork = Integer.parseInt(countGroupsOfWork);

    }
    if (countTypesOfWork.contains("не")){
      this.countTypesOfWork =0;
    } else{
      this.countTypesOfWork = Integer.parseInt(countTypesOfWork);
    }
    this.name = name;
    this.statusofLoad = statusofLoad;
    this.sumOfGeneralContractor = sumOfGeneralContractor;

    if (protocolDate.contains("не задано")){
      this.protocolDate = null;

    }else {
      DateTimeFormatter formatter = DateTimeFormat.forPattern( "dd.MM.YYYY H:mm:ss");
      this.protocolDate = formatter.parseDateTime(protocolDate).toDate();
    }

    String[] tempName = (name + "-").split("-");
    if (tempName.length == 6) {
      String inn = tempName[3].trim();
      String reestrNumber = tempName[4].trim();
      Organisation organisation = Organisation.find("inn = ? and scope = ? and statusofload = ?", inn, scope, objectStatusofLoad.INBASE.ordinal()).first();
      if (organisation!=null){
        if (organisation.registrNumber.contains("Не задан")){
          organisation.registrNumber = reestrNumber;
          organisation.save();
        } else {
        }
        this.organisation = organisation;
      } else {
      }
    } else {
    }
  }

  public static int deleteOnLoadStatusCert(objectStatusofLoad objectStatusofLoad, Scope scope) {
    int deleteCount = 0;
    ArrayList<Certificate> certificates = (ArrayList) Certificate.find("statusofload = ? ", objectStatusofLoad.ordinal()).fetch();
    if (certificates.size() > 0) {
      for (Certificate certificate : certificates) {
        if (certificate.organisation != null) {
          if (certificate.organisation.scope == scope) {
            certificate.delete();
            deleteCount++;
          }
        }
      }
    }

    return  deleteCount;
  }


  public boolean equals1cData(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Certificate that = (Certificate) o;

    if (countGroupsOfWork != that.countGroupsOfWork) return false;
    if (countTypesOfWork != that.countTypesOfWork) return false;
    if (!name.equals(that.name)) return false;
    if (!organisation.equals(that.organisation)) return false;

    return true;
  }




}
