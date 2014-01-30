package models;


public enum SettingType {
  FILES_ORGANISATION_1C("Файлы для загрузки организаций из 1С"),
  FILES_CERTIFICATE_1C("Файлы для загрузки свидетельств из 1С "),
  FILES_EXCEL_REESTR("Файлы для загрузки из Экселевского реестра"),
  OTHERS("Другое");

  SettingType(String s) {
    this.text = s;
  }
  private final String text;

}
