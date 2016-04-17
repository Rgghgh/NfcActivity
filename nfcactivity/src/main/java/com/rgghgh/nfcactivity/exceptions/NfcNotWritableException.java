package com.rgghgh.nfcactivity.exceptions;

/**
 * Thrown when attempting to write any data on a "Read Only" tag.
 * @since v1.0
 */
public class NfcNotWritableException extends Exception
{
    public NfcNotWritableException()
    {
        super();
    }

    public NfcNotWritableException(String msg)
    {
        super(msg);
    }
}
