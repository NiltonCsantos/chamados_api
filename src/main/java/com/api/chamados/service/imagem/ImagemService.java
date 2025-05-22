package com.api.chamados.service.imagem;

import org.springframework.web.multipart.MultipartFile;

public interface ImagemService {
    String upload(MultipartFile file);
    String getFileAsBase64(String fileName);
}
