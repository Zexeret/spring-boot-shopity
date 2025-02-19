package com.ecommerce.shopity.utils;

import com.ecommerce.shopity.exceptions.APIException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileService {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void uploadImage(String path, MultipartFile image, String filename) {
        try{
            String filePath = path + File.separator + filename;

            File folder = new File(path);
            if (!folder.exists()) {

               folder.mkdirs();
            }

            // Upload to server
            Files.copy(image.getInputStream(), Paths.get(filePath)) ;
        }catch (Exception e){
            throw new APIException("Image upload failed, error = " + e.getMessage());
        }

    }
}
