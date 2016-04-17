package com.rgghgh.nfcactivitydemo;

import android.nfc.FormatException;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.rgghgh.nfcactivity.NfcActivity;
import com.rgghgh.nfcactivity.NfcConnection;
import com.rgghgh.nfcactivity.NfcTester;
import com.rgghgh.nfcactivity.exceptions.NfcNotWritableException;
import com.rgghgh.nfcactivity.exceptions.NfcTextTooLargeException;

import java.io.IOException;

public class MainActivity extends NfcActivity
{
    private TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tvDisplay = (TextView) findViewById(R.id.tvDisplay);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        runNfcTest(); // see below
    }

    @Override
    public void onNfcStart(NfcConnection conn)
    {
        try
        {
            String id = conn.getTagId(); // get unique tag id
            String data = conn.read(); // get string data from tag

            if(!data.isEmpty())
                setDisplay(id + " - " + data);

            conn.write("Hello, World!"); // write string data to tag
            conn.write("Hello, World!", this); // write string data to tag + application record
            conn.makeReadOnly(); // make tag "read only"
        }
        catch (FormatException e)
        {
            setDisplay("The data on this tag is malformed.");
        }
        catch (IOException e)
        {
            setDisplay("The tag was disconnected mid-action");
        }
        catch (NfcNotWritableException e)
        {
            setDisplay("Nfc tag is set to 'Read Only' and can not be changed.");
        }
        catch (NfcTextTooLargeException e)
        {
            setDisplay("This data is too large for this tag, try another tag or smaller data.");
        }
    }

    public void runNfcTest()
    {
        // NfcTester object usage:
        NfcTester tester = new NfcTester(this);

        if(!tester.hasNfc()) // same as this.hasNfc()
            setDisplay("Device is not NFC compatible.");

        if(!tester.isNfcEnabled()) // same as this.isNfcEnabled()
            setDisplay("NFC is disabled. Please enable in settings.");

        if(!tester.hasNfcPermission()) // same as this.hasNfcPermission()
            setDisplay("NFC usage permission has not been granted.");
    }

    public void setDisplay(String text)
    {
        this.tvDisplay.setText(text);
    }
}
