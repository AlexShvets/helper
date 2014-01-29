package models;

public enum objectStatusofLoad {
  INBASE("В базе") ,
  LOADED("Получен по обмену");

  objectStatusofLoad(String s) {
    this.text = s;
  }

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
