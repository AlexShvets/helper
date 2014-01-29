package models;

import play.db.jpa.GenericModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "field_seq", sequenceName = "field_seq")
public class Field extends GenericModel {
  @Id
  @GeneratedValue(generator = "field_seq")
  int id;

  @Column(nullable = false)
  public String paramName;



}
