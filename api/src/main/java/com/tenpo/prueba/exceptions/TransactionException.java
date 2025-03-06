package com.tenpo.prueba.exceptions;

import java.io.Serial;

public class TransactionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -569438086422779995L;

    public TransactionException() {
        super();
    }

    public TransactionException(String aMensaje) {
        super(aMensaje);
    }

    public TransactionException(String aMensaje, boolean aPosibilidadContinuar) {
        super(aMensaje, aPosibilidadContinuar ? new Throwable("true") : null);
    }

    public TransactionException(String aMensaje, Throwable aCausa) {
        super(aMensaje, aCausa);
    }

    public TransactionException(String aMensaje, Exception aException) {
        super(aMensaje, aException);
    }

    public TransactionException(String aMensaje, Throwable aCausa, boolean enableSuppression, boolean writableStackTrace) {
        super(aMensaje, aCausa, enableSuppression, writableStackTrace);
    }
}
