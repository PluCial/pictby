package com.pictby.exception;

public class ObjectNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "対象するオブジェクトがありません。";
    
    /**
     * コンストラクタ
     */
    public ObjectNotFoundException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }

}
