package com.rgghgh.nfcactivity.exceptions;

/**
 * Thrown when a nfc tag is disconnected while actions are being performed on it.
 * @since v1.0
 */
public class NfcDisconnectedException extends Exception
{
    public NfcDisconnectedException()
    {
        super();
    }

    public NfcDisconnectedException(String msg)
    {
        super(msg);
    }
}
