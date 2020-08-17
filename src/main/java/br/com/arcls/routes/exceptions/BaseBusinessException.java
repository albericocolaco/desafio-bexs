package br.com.arcls.routes.exceptions;

import br.com.arcls.routes.exceptions.enums.Errors;

public abstract class BaseBusinessException extends RuntimeException {

    public BaseBusinessException(){
        super();
    }
    public BaseBusinessException(final Errors errors){
        super(errors.getMessage());
    }
    public BaseBusinessException(final Throwable throwable){
        super(throwable);
    }
    public BaseBusinessException(final Errors errors, final Throwable throwable){
        super(errors.getMessage(), throwable);
    }

}
