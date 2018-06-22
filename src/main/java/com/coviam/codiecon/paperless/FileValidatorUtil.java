package com.coviam.codiecon.paperless;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Sushil on 22/06/18.
 */
public class FileValidatorUtil {

    public static void validateUploadedFile(MultipartFile request, String docType) {
        validateName(request.getName(), docType);
    }

    private static boolean validateName(String name, String docType) {
        return name.startsWith(docType);
    }
}
