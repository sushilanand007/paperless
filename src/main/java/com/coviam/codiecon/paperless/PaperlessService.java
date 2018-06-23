package com.coviam.codiecon.paperless;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperlessService {

  private static final String HYPHEN = "-";
  private static final String APPLICATION_NAME = "paperless-web";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  private static final String CLIENT_ID =
      "406326851056-7uj1fu5k8t4bc4mguron4fjl7k2tgq0t.apps.googleusercontent.com";
  private static final String CLIENT_SECRET = "a2uzHX3MGBi2H5mkhuzVQmqo";
  private static final String REFRESH_TOKEN_GOOGLE =
      "1/0rCvzYCOPYytzoxpqhI2rMmQhBwLJ_f3OB72Zzh59tM";

  @Autowired
  private UserService userService;

  private Credential getCredentials() throws IOException, GeneralSecurityException {
    RefreshTokenRequest request =
        new GoogleRefreshTokenRequest(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
            REFRESH_TOKEN_GOOGLE, CLIENT_ID, CLIENT_SECRET);

    TokenResponse response = request.execute();

    return new GoogleCredential.Builder().setClientSecrets(CLIENT_ID, CLIENT_SECRET)
        .setJsonFactory(JSON_FACTORY).setTransport(new NetHttpTransport()).build()
        .setFromTokenResponse(response);
  }

  private Drive getDriveService() throws IOException, GeneralSecurityException {
    NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
        .setApplicationName(APPLICATION_NAME).build();
  }

  public List<String> getMissingDocument(String name) throws IOException, GeneralSecurityException {
    List<DocumentTypes> missingDocuments = new ArrayList<>(Arrays.asList(DocumentTypes.values()));
    String nameQuery = "name contains '" + name + "'";
    FileList result =
        getDriveService().files().list().setIncludeTeamDriveItems(true).setPageSize(10)
            .setQ(nameQuery).setSupportsTeamDrives(true).setPageSize(10).execute();
    List<File> files = result.getFiles();
    if (files != null && !files.isEmpty()) {
      for (File file : files) {
        String fileName = file.getName();
        for (DocumentTypes document : DocumentTypes.values()) {
          if (fileName.contains(document.name())) {
            missingDocuments.remove(document);
          }
        }
      }
    }
    List<String> returnValue = new ArrayList<>();
    for (DocumentTypes missingDocument : missingDocuments) {
      returnValue.add(missingDocument.getUiMenuLabel());
    }
    return returnValue;
  }

  public boolean uploadDocument(DocumentTypes documentType, String name, java.io.File file,
      String mimeType) throws IOException, GeneralSecurityException {
    String folderQuery =
        "name = '" + name + "' and mimeType = 'application/vnd.google-apps.folder'";
    List<File> result =
        getDriveService().files().list().setIncludeTeamDriveItems(true).setPageSize(10)
            .setQ(folderQuery).setSupportsTeamDrives(true).setPageSize(1).execute().getFiles();
    String folderId = null;
    if (null == result || result.isEmpty()) {
      File fileMetadata = new File();
      fileMetadata.setName(name);
      fileMetadata.setMimeType("application/vnd.google-apps.folder");

      folderId = getDriveService().files().create(fileMetadata).setFields("id").execute().getId();
    } else {
      folderId = result.get(0).getId();
    }

    File fileMetadata = new File();
    String fileName = name + HYPHEN + documentType.name();
    fileMetadata.setName(fileName);
    fileMetadata.setParents(Collections.singletonList(folderId));

    FileContent mediaContent = new FileContent(mimeType, file);
    File fileUploaded =
        getDriveService().files().create(fileMetadata, mediaContent).setSupportsTeamDrives(true)
            .setFields("id, parents").execute();

    return true;
  }

  public Map<String, List<String>> missingDocumentsReport()
      throws IOException, GeneralSecurityException {
    List<String> users = userService.getUserList(null);
    if (!users.isEmpty()) {
      Map<String, List<String>> result = new HashMap<>();
      for (String user : users) {
        result.put(user, getMissingDocument(user));
      }
      return result;
    }
    return null;
  }
}
