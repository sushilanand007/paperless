package com.coviam.codiecon.paperless;

import java.util.HashMap;
import java.util.Map;

public enum DocumentTypes {
  PASSPORT("Passport"), PAN_CARD("Pan card"), EXPERIENCE_LETTER(
      "Previous employer experience letter"), PAY_SLIP("Pay slip 1");

  private static Map<String, DocumentTypes> uiMenuLabelMap = new HashMap<>();

  static {
    for (DocumentTypes documentType : DocumentTypes.values()) {
      uiMenuLabelMap.put(documentType.getUiMenuLabel(), documentType);
    }
  }

  private String uiMenuLabel;

  DocumentTypes(String uiMenuLabel) {
    this.uiMenuLabel = uiMenuLabel;
  }

  public String getUiMenuLabel() {
    return uiMenuLabel;
  }

  public static DocumentTypes getByUiMenuLabel(String uiMenuLabel) {
    return uiMenuLabelMap.get(uiMenuLabel);
  }
}
