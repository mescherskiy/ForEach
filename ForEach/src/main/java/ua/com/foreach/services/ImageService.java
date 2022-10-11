package ua.com.foreach.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class ImageService {

    @Transactional
    public void saveAvatar(MultipartFile imageFile, String fileName) throws IOException {

        String folder = "src/main/resources/static/avatars/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + fileName);
        Files.write(path, bytes);
    }
}
