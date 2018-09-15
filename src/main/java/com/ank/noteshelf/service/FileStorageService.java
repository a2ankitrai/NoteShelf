package com.ank.noteshelf.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ank.noteshelf.response.PictureResponse;
import com.google.cloud.storage.Blob;

public interface FileStorageService {

    public PictureResponse saveFile(MultipartFile file) throws IOException;

    public boolean deleteFile(String mediaLink);

    public Blob getFile(String fileName);
}
