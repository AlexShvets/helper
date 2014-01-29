package models;

public enum  InsuranceContractStatus {
  NEW("Новый"),
  LOADED("Загружен"),
  PAYED("Оплачен"),
  HASNEXT("Отправлен в Саратов"),
  NOTACTUAL("Не актуален");

  InsuranceContractStatus(String s) {
    this.text =s;
  }
  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
