package models;


public enum certificateStatusInSro {
  NEW("На коммиссию"),
  CONFIRM("Подтвержден"),
  REISSUED("Переоформлен"),
  CANSELED("Аннулирован");

  certificateStatusInSro(String s) {
    this.text = s;
  }
  private final String text;

  public static certificateStatusInSro getStatusFromString (String stringStatus) {
    if (stringStatus.toLowerCase().contains("Аннулирован".trim().toLowerCase())) return CANSELED;
    if (stringStatus.toLowerCase().contains("Подтвержден".trim().toLowerCase())) return CONFIRM;
    if (stringStatus.toLowerCase().contains("Переоформлен".trim().toLowerCase())) return REISSUED;
    return NEW;
  }

  @Override
  public String toString() {
    return text;
  }
}
