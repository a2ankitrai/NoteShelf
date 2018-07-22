package com.ank.noteshelf.exception;

import lombok.Data;

/**
 * The ExceptionDetail class models information about a web service request which results in an Exception. This
 * information may be returned to the client.
 *   
 */
@Data
public class ExceptionDetail {

	  /**
     * A timestamp expressed in milliseconds.
     */
    private long timestamp;
    /**
     * The HTTP method (e.g. GET, POST, etc.)
     */
    private String method = "";
    /**
     * The web service servlet path.
     */
    private String path = "";
    /**
     * The HTTP status code of the response.
     */
    private int status;
    /**
     * The text description of the HTTP status code of the response.
     */
    private String statusText = "";
    /**
     * The fully qualified Class name of the Exception.
     */
    private String exceptionClass = "";
    /**
     * The value of the Exception message attribute.
     */
    private String exceptionMessage = "";

    /**
     * Construct an ExceptionDetail.
     */
    public ExceptionDetail() {
        this.timestamp = System.currentTimeMillis();
    }
    
}
