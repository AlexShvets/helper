package models;

import play.db.jpa.GenericModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "setting_seq", sequenceName = "setting_seq")

public class Setting extends GenericModel{
  @Id
  @GeneratedValue(generator = "setting_seq")
  int id;

  @Column
  public String name;

  @Column
  public String value;

  @Column
  public Scope scope;

  @Column
  public SettingType settingType;

  public Setting(String name, Scope scope, SettingType settingType, String value) {
    this.name = name;
    this.scope = scope;
    this.settingType = settingType;
    this.value = value;
  }
}
