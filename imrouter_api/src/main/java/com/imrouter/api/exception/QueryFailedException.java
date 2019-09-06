package com.imrouter.api.exception;

public class QueryFailedException extends NullPointerException {

    public QueryFailedException(){
        super();
    }

    public QueryFailedException(String msg){
        super(msg);
    }
}
