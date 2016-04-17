package com.rgghgh.nfcactivity.exceptions;

/**
 * Thrown when attempting to write data that is too large, in memory size, for the connected tag.
 * @since v1.0
 */
public class NfcTextTooLargeException extends Exception
{
    public NfcTextTooLargeException()
    {
        super();
    }

    public NfcTextTooLargeException(String msg)
    {
        super(msg);
    }
}
