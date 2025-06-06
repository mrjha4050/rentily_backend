package com.backend.backend.Service.ServiceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;
    private static final int MAX_IMAGES = 4;

    public CloudinaryService (
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public List<String> uploadImages(MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }

        if (files.length > MAX_IMAGES) {
            throw new IOException("Maximum " + MAX_IMAGES + " images allowed");
        }

        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                urls.add((String) uploadResult.get("secure_url"));
            }
        }
        return urls;
    }

    public String uploadImage(MultipartFile file) throws IOException{
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            throw  new IOException("failed to upload image to cloudinary: " + e.getMessage(),e);
        }
    }


}
