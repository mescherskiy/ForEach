package ua.com.foreach.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.services.ImageService;
import ua.com.foreach.services.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@AllArgsConstructor
@RequestMapping("api/")
public class UploadImageController {
    private UserService userService;
    private ImageService imageService;

    @PostMapping("/uploadImage")
    public ResponseEntity uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        String login = UserService.getCurrentUser().getUsername();
        CustomUser customUser = userService.findByLogin(login);
        Long userId = customUser.getId();
        String fileName = userId + ".jpg";

        try {
            imageService.saveAvatar(imageFile, fileName);
            userService.updateAvatar(customUser, fileName);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

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
                        contentType(MediaType.parseMediaType("image/jpeg")).
                        body(byteArrayResource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/deleteImage")
    public ResponseEntity deleteImage() {
        CustomUser user = userService.findByLogin(UserService.getCurrentUser().getUsername());

        user.setAvatarFileName("default.jpg");

        return ResponseEntity.ok().build();
    }
}


