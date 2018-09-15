package com.ank.noteshelf.response;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PictureResponse {

    private String pictureName;
    private String downloadUri;
    private String contentType;
    private Resource pictureResource;
    private long size;
    
}
