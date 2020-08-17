package br.com.arcls.routes.exceptions.enums;

public enum Errors {
    ROT001("Error to proccess the file");

    private String message;

    Errors(final String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
