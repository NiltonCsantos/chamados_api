//package com.api.chamados.service.azure.impl;
//
//import com.api.chamados.service.azure.AzureService;
//import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobContainerClientBuilder;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Service
//@Profile("prod")
//@Slf4j
//public class AzureServiceImpl implements AzureService {
//
//    private final BlobContainerClient containerClient;
//
//    public AzureServiceImpl(
//            @Value("${azure.storage.connection-string}") String connectionString,
//            @Value("${azure.storage.container-name}") String containerName) {
//
//        this.containerClient = new BlobContainerClientBuilder()
//                .connectionString(connectionString)
//                .containerName(containerName)
//                .buildClient();
//
//        if (!this.containerClient.exists()) {
//            this.containerClient.create();
//        }
//    }
//
//    @Override
//    public String upload(MultipartFile file) {
//        try {
//            String fileName = file.getOriginalFilename();
//            BlobClient blobClient = containerClient.getBlobClient(fileName);
//
//            blobClient.upload(file.getInputStream(), file.getSize(), true);
//
//            return blobClient.getBlobUrl();
//        }catch (IOException e){
//            log.error("Erro ao fazer upload da imagem: {}", e.getMessage());
//            throw new RuntimeException(e.getCause());
//        }
//    }
//}
