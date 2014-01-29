package models;


public enum  Scope {
  SMR("СМР"),
  PR("ПР"),
  ENERGY("Энергоаудит"),
  INZIZ("ИнжИз");


  private  String text;

  private Scope(String s) {
    this.text = s;
  }

  @Override
  public String toString() {
    return text;
  }
}
