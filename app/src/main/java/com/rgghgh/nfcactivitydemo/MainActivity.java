package com.rgghgh.nfcactivitydemo;

import android.nfc.FormatException;
import android.os.Bundle;

import com.rgghgh.nfcactivity.NfcActivity;
import com.rgghgh.nfcactivity.NfcConnection;
import com.rgghgh.nfcactivity.exceptions.NfcNotWritableException;
import com.rgghgh.nfcactivity.exceptions.NfcTextTooLargeException;

import java.io.IOException;

public class MainActivity extends NfcActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onNfcStart(NfcConnection conn)
    {
        try
        {
            // get unique tag id
            String id = conn.getTagId();

            // get string data from tag
            String data = conn.read();

            // write string data to tag
            conn.write("Hello, World!");

            // write string data to tag + application record
            conn.write("Hello, World!", this);

            // make tag "read only"
            conn.makeReadOnly();
        }
        catch (FormatException | IOException | NfcNotWritableException | NfcTextTooLargeException e)
        {
            e.printStackTrace();
        }
    }
}
