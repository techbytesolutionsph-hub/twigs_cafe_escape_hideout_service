package ph.com.tteh.controller.backend.cloudinary;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ph.com.tteh.service.api.cloudinary.CloudinaryService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Cloudinary API Controller")
@RequestMapping("/api/v1/cloudinary")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) {

        try {
            Map<?, ?> result = cloudinaryService.uploadPhoto(file);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Unable to upload profile photo."));
        }
    }

    @DeleteMapping("/delete-image")
    public ResponseEntity<?> deleteImage(@RequestParam("publicId") String publicId) {

        try {
            Map<?, ?> response = cloudinaryService.deleteImage(publicId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Unable to delete image. Please try again."));
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(cloudinaryService.uploadDocument(file));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDocument(@RequestParam("publicId") String publicId) throws Exception {
        return ResponseEntity.ok(cloudinaryService.deleteDocument(publicId));
    }
}
