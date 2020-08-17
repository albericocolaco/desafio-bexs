package br.com.arcls.routes.exceptions;

import br.com.arcls.routes.exceptions.enums.Errors;

public class FileSystemProviderException extends BaseBusinessException {

    public FileSystemProviderException() {
        super(Errors.ROT001);
    }
}
