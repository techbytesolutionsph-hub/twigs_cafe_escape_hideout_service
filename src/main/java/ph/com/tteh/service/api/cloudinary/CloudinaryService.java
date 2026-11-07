package ph.com.tteh.service.api.cloudinary;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ph.com.tteh.configuration.properties.CloudinaryPropertiesConfig;
import ph.com.tteh.exception.ServiceException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CloudinaryService {

    private final CloudinaryPropertiesConfig config;
    private final RestTemplate restTemplate;

    private static final long MAX_FILE_SIZE = 1024 * 1024;
    private static final long MAX_DOCUMENT_SIZE = 5 * 1024 * 1024;

    private static final Map<String, String> ALLOWED_DOCUMENT_TYPES = Map.of(
            "pdf", "application/pdf",
            "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    );

    public Map<?, ?> uploadPhoto(MultipartFile file) throws Exception {

        this.validatePhoto(file);

        String uploadUrl = config.buildUploadImageUrl();

        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }
                };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);
        body.add("upload_preset", config.getImageUploadPreset());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(uploadUrl, request, Map.class);

        Map<?, ?> result = response.getBody();

        if (result == null || result.get("secure_url") == null || result.get("public_id") == null) {
            throw new ServiceException("Invalid response received from Cloudinary.");
        }

        return Map.of(
                "secure_url", result.get("secure_url"),
                "public_id", result.get("public_id")
        );
    }

    public Map<?, ?> deleteImage(String publicId) throws Exception {

        if (publicId == null || publicId.isBlank()) {
            throw new IllegalArgumentException(
                    "Photo public ID is required."
            );
        }

        long timestamp = Instant.now().getEpochSecond();

        Map<String, String> parameters = new TreeMap<>();
        parameters.put("public_id", publicId);
        parameters.put("timestamp", String.valueOf(timestamp));

        StringBuilder signatureData = new StringBuilder();

        parameters.forEach((key, value) ->
                signatureData.append(key)
                        .append("=")
                        .append(value)
                        .append("&")
        );

        signatureData.setLength(signatureData.length() - 1);
        signatureData.append(config.getApiSecret());

        String signature = generateSha1(signatureData.toString());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("public_id", publicId);
        body.add("timestamp", String.valueOf(timestamp));
        body.add("api_key", config.getApiKey());
        body.add("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(config.buildDeleteImageUrl(), request, Map.class);

        Map<?, ?> result = response.getBody();

        if (result == null || !"ok".equals(result.get("result"))) {
            throw new ServiceException("Cloudinary photo deletion failed.");
        }

        return result;
    }

    public Map<?, ?> uploadDocument(MultipartFile file) throws Exception {

        this.validateDocument(file);

        String uploadUrl = config.buildUploadDocumentUrl();

        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);
        body.add("upload_preset", config.getDocumentUploadPreset());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                uploadUrl, request, Map.class
        );

        Map<?, ?> result = response.getBody();

        if (result == null
                || result.get("secure_url") == null
                || result.get("public_id") == null
                || result.get("bytes") == null) {
            throw new ServiceException(
                    "Invalid response received from Cloudinary."
            );
        }

        long bytes = ((Number) result.get("bytes")).longValue();

        Map<String, Object> uploadResult = new LinkedHashMap<>();

        uploadResult.put("secure_url", result.get("secure_url"));
        uploadResult.put("public_id", result.get("public_id"));
        uploadResult.put("original_filename", result.get("original_filename"));
        uploadResult.put("format", result.get("format"));
        uploadResult.put("bytes", bytes);
        uploadResult.put("size_mb", String.format(
                Locale.US, "%.2f", bytes / (1024.0 * 1024.0)
        ));
        uploadResult.put("resource_type", result.get("resource_type"));

        return uploadResult;
    }

    public Map<?, ?> deleteDocument(String publicId) throws Exception {

        if (publicId == null || publicId.isBlank()) {
            throw new IllegalArgumentException("Document public ID is required.");
        }

        return this.deleteResource(publicId, "raw", config.buildDeleteDocumentUrl());
    }

    private String generateSha1(String value) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }

    private void validatePhoto(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please select an image.");
        }

        String contentType = file.getContentType();

        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            throw new IllegalArgumentException("Only JPG and PNG images are allowed.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Maximum image size is 1 MB.");
        }
    }


    private Map<?, ?> deleteResource(String publicId, String resourceType, String deleteUrl) throws Exception {

        long timestamp = Instant.now().getEpochSecond();

        Map<String, String> parameters = new TreeMap<>();
        parameters.put("invalidate", "true");
        parameters.put("public_id", publicId);
        parameters.put("timestamp", String.valueOf(timestamp));

        StringBuilder signatureData = new StringBuilder();

        parameters.forEach((key, value) ->
                signatureData.append(key)
                        .append("=")
                        .append(value)
                        .append("&")
        );

        signatureData.setLength(signatureData.length() - 1);
        signatureData.append(config.getApiSecret());

        String signature = generateSha1(signatureData.toString());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("public_id", publicId);
        body.add("timestamp", String.valueOf(timestamp));
        body.add("api_key", config.getApiKey());
        body.add("signature", signature);
        body.add("invalidate", "true");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                deleteUrl, request, Map.class
        );

        Map<?, ?> result = response.getBody();

        if (result == null || !"ok".equals(result.get("result"))) {
            throw new ServiceException("Cloudinary resource deletion failed.");
        }

        return result;
    }


    private void validateDocument(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please select a document.");
        }

        if (file.getSize() > MAX_DOCUMENT_SIZE) {
            throw new IllegalArgumentException("Maximum document size is 5 MB.");
        }

        String originalFilename = file.getOriginalFilename();

        String extension = StringUtils.getFilenameExtension(
                originalFilename == null ? "" : originalFilename
        );

        if (extension == null) {
            throw new IllegalArgumentException("Invalid document extension.");
        }

        String allowedContentType = ALLOWED_DOCUMENT_TYPES.get(extension.toLowerCase(Locale.ROOT));

        if (allowedContentType == null || !allowedContentType.equalsIgnoreCase(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF, DOCX, and XLSX files are allowed.");
        }
    }
}