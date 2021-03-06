package com.coviam.codiecon.paperless;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileModel {
  private String documentType;
  private String name;
  private MultipartFile file;


  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }
}
