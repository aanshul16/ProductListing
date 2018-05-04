package com.productlisting.http.apimodel;

/**
 * This class is to keep response in case of api fails
 */

public class ErrorEvent {
    private int errorCode;
    private String errorMsg;

    public ErrorEvent(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ErrorEvent(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
