package com.coviam.codiecon.paperless;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class PaperlessService {

  private static final String APPLICATION_NAME = "paperless-web";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  private static final String CLIENT_ID =
      "406326851056-7uj1fu5k8t4bc4mguron4fjl7k2tgq0t.apps.googleusercontent.com";
  private static final String CLIENT_SECRET = "a2uzHX3MGBi2H5mkhuzVQmqo";
  private static final String REFRESH_TOKEN_GOOGLE =
      "1/xaInQT_jvH6ZQYmRnLPUipfhL8MRbc4MuGfx1h_BOIU";
  // Directory to store user credentials.

  private static Credential getCredentials() throws IOException, GeneralSecurityException {
    // Load client secrets.
    RefreshTokenRequest request =
        new GoogleRefreshTokenRequest(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
            REFRESH_TOKEN_GOOGLE, CLIENT_ID, CLIENT_SECRET);

    TokenResponse response = request.execute();

    return new GoogleCredential.Builder().setClientSecrets(CLIENT_ID, CLIENT_SECRET)
        .setJsonFactory(JSON_FACTORY).setTransport(new NetHttpTransport()).build()
        .setFromTokenResponse(response);
  }

  private static Drive getDriveService() throws IOException, GeneralSecurityException {
    NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
        .setApplicationName(APPLICATION_NAME).build();
  }

  public static void main(String... args) throws IOException, GeneralSecurityException {
    // Print the names and IDs for up to 10 files.
    FileList result =
        getDriveService().files().list().setPageSize(10).setFields("nextPageToken, files(id, name)")
            .execute();
    List<File> files = result.getFiles();
    if (files == null || files.isEmpty()) {
      System.out.println("No files found.");
    } else {
      System.out.println("Files:");
      for (File file : files) {
        System.out.printf("%s (%s)\n", file.getName(), file.getId());
      }
    }
  }

}
