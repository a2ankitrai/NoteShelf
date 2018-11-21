package com.ank.noteshelf.service.impl;

import static com.ank.noteshelf.resource.NsCommonConstant.CACHE_APP_CONFIG;
import static com.ank.noteshelf.resource.NsCommonConstant.GCP_CREDENTIALS;
import static com.ank.noteshelf.resource.NsCommonConstant.NS_PROFILE_IMAGES;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ank.noteshelf.response.PictureResponse;
import com.ank.noteshelf.service.FileStorageService;
import com.ank.noteshelf.util.UuidUtils;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
@Order(value = 1)
@Qualifier("googleCloudStorageService")
public class GoogleCloudStorageServiceImpl implements FileStorageService {

    @Autowired
    private CacheManager cacheManager;

    private Storage storage;
    private Bucket bucket;
    private boolean bucketInitialized;

    private void initializeBucket() {

	if (!bucketInitialized)
	    return;

	Credentials credentials = null;

	String gcsCredentials = (String) cacheManager.getCache(CACHE_APP_CONFIG).get(GCP_CREDENTIALS).get();

	InputStream credentialsStream = new ByteArrayInputStream(gcsCredentials.getBytes(StandardCharsets.UTF_16));
	try {
	    credentials = GoogleCredentials.fromStream(credentialsStream);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	if (credentials != null) {
	    storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(NS_PROFILE_IMAGES).build()
		    .getService();
	    bucket = storage.get(NS_PROFILE_IMAGES);
	    this.bucketInitialized = true;
	}
    }

    private boolean checkBucketInitiatlization() {
	return this.bucketInitialized;
    }

    @Override
    public PictureResponse saveFile(MultipartFile file) throws IOException {

	if (!checkBucketInitiatlization()) {
	    initializeBucket();
	}

	Blob blob = null;
	PictureResponse pictureResponse = null;
	String storedFileName = UuidUtils.generateRandomUuid().toString() + "_" + file.getOriginalFilename();

	blob = bucket.create(storedFileName, file.getBytes(), file.getContentType());

	pictureResponse = new PictureResponse();
	pictureResponse.setPictureName(storedFileName);
	pictureResponse.setContentType(file.getContentType());
	pictureResponse.setDownloadUri(blob.getMediaLink());
	pictureResponse.setSize(blob.getSize());

	return pictureResponse;
    }

    @Override
    public boolean deleteFile(String mediaLink) {

	if (!checkBucketInitiatlization()) {
	    initializeBucket();
	}

	BlobId blobId = BlobId.of(bucket.getName(), mediaLink);
	boolean deleted = storage.delete(blobId);
	return deleted;
    }

    @Override
    public Blob getFile(String fileName) {

	if (fileName == null || fileName.isEmpty())
	    return null;

	if (!checkBucketInitiatlization()) {
	    initializeBucket();
	}

	Blob blob = bucket.get(fileName);

	return blob;
    }

}
