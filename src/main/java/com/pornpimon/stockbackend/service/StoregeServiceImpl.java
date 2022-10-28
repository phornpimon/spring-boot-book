package com.pornpimon.stockbackend.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.pornpimon.stockbackend.exception.StorageException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoregeServiceImpl implements StoregeService{

    // @Value("${app.upload.part:images}")
    // private String path;

    // private Path rootLocation;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3Client amazonS3Client;


    public StoregeServiceImpl(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
        
    }

    // @Override
    // public void init() {
    //     this.rootLocation = Paths.get(path);
    //     try {
    //         Files.createDirectories(rootLocation);
    //     } catch (IOException e) {
    //         throw new StorageException("message" + e);
    //     }
        
    // }

    @Override
    public String store(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        var fileNameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        var key = UUID.randomUUID().toString() + "." + fileNameExtension;

        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3Client.putObject(bucketName, key, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new StorageException("message" + fileNameExtension + e.getMessage());
        }

        // amazonS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
        amazonS3Client.getResourceUrl(bucketName, key);

        return key;

        


        // String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // try {
        //     if (fileName.contains("..")) {
        //         throw new StorageException("message");
        //     }
        //     fileName = UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);

        //     try(InputStream inputStream = file.getInputStream()) {
        //         Files.copy(inputStream, this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        //         return fileName;
        //     }
            
        // } catch (IOException e) {
        //     throw new StorageException("message" + fileName + e.getMessage());
        // }
    }
    
}
