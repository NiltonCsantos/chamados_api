package com.api.chamados.service.imagem.impl;

import com.api.chamados.service.imagem.ImagemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
@Service
public class LocalServiceImpl implements ImagemService {
    private static final String LOCAL_PATH = System.getProperty("java.io.tmpdir");
    @Override
    public String upload(MultipartFile file) {
        try {
            File dir = new File(LOCAL_PATH);
            if (!dir.exists())
                dir.mkdirs();

            String filePath = LOCAL_PATH + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            return filePath;
        }catch (IOException e){
            log.error("Erro ao fazer upload da imagem: {}", e.getMessage());
            throw new RuntimeException(e.getCause());
        }
    }

    @Override
    public String getFileAsBase64(String fileName) {
        String base64ComPrefixo = "data:image/png;base64,";
        try {
            File file = new File(fileName);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return base64ComPrefixo+Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            log.error("Erro ao converter o arquivo para Base64: {}", e.getMessage());
            throw new RuntimeException("Erro ao ler o arquivo para Base64", e);
        }
    }
}
