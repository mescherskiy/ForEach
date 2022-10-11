package ua.com.foreach.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("api/")
public class UploadImageController {

    @GetMapping({"getImage/{photo}", "user/getImage/{photo}"})
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo){
        if(!photo.equals("") || photo!=null){
            try {
                Path fileName = Paths.get("src/main/resources/static/avatars", photo);
                byte[] buffer = Files.readAllBytes(fileName);
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);

                return ResponseEntity.ok().
                        contentLength(buffer.length).
                        contentType(MediaType.parseMediaType("image/jpg")).
                        body(byteArrayResource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
