package com.coviam.codiecon.paperless;

public enum DocumentTypes {
  PASSPORT("Passport"), PAN_CARD("Pan card"), EXPERIENCE_LETTER(
      "Previous employer experience letter"), PAY_SLIP("Pay slip 1");

  private String uiMenuLabel;

  DocumentTypes(String uiMenuLabel) {
    this.uiMenuLabel = uiMenuLabel;
  }

  public String getUiMenuLabel() {
    return uiMenuLabel;
  }
}
