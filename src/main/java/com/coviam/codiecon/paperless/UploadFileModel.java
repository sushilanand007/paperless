package com.coviam.codiecon.paperless;

public class UploadFileModel {
  private DocumentTypes documentType;
  private String name;

  public DocumentTypes getDocumentType() {
    return documentType;
  }

  public void setDocumentType(DocumentTypes documentType) {
    this.documentType = documentType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
