package com.pictby.exception;

public class PermissionException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "権限がありません。";
    
    /**
     * コンストラクタ
     */
    public PermissionException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public PermissionException(String message) {
        super(message);
    }

}
