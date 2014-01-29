package models;

import play.db.jpa.GenericModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Entity
@SequenceGenerator(name = "inscontr_seq", sequenceName = "inscontr_seq")
public class InsuranceContract extends GenericModel {
  @Id
  @GeneratedValue(generator = "inscontr_seq")
  int id;
  @Column(nullable = true)
  public String insuranceCompany;

  @Column(nullable = true)
  public Date dateBegin;

  @Column(nullable = true)
  public Date dateEnd;

  @ManyToOne
  public Organisation organisation;

  public InsuranceContractStatus status;
}
