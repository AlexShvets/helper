package models;

public enum OrganisationStatusInSro {
  CANDIDAT("Кандидат"),
  MEMBER("Участник"),
  DISSMISSED("Исключен");
  private  String text;

  private OrganisationStatusInSro(String s) {
    this.text = s;
  }

  @Override
  public String toString() {
    return text;
  }

  public static OrganisationStatusInSro getStatusFromString (String stringStatus) {
        if (stringStatus.toLowerCase().contains("Член СРО".trim().toLowerCase())) return MEMBER;
        if (stringStatus.toLowerCase().contains("Исключен".trim().toLowerCase())) return DISSMISSED;
    return CANDIDAT;

  }
}
