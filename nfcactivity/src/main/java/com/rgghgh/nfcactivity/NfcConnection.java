package com.rgghgh.nfcactivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;

import com.rgghgh.nfcactivity.exceptions.NfcNotWritableException;
import com.rgghgh.nfcactivity.exceptions.NfcTextTooLargeException;

import java.io.IOException;

/**
 * <p>the <b>NfcConnection </b> object is used to operate on an Nfc Tag.</p>
 * @since v1.0
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public final class NfcConnection
{
    private Ndef tag;
    private byte[] tagId;

    protected NfcConnection(Tag tagFromIntent)
    {
        this.tag = Ndef.get(tagFromIntent);
        this.tagId = tagFromIntent.getId();
    }

    /**
     * <p>Gets string data from the contacted nfc tag and returns it.</p>
     * @return Data read from nfc tag connected
     * @throws IOException If there is an I/O failure or card disconnected.
     * @throws FormatException If the data on the card is malformed.
     * @since v1.0
     */
    public String read() throws IOException, FormatException
    {
        this.tag.connect();
        String data = new String(this.tag.getNdefMessage().getRecords()[0].getPayload()).substring(3);
        this.tag.close();
        return data;
    }

    /**
     * Writes string data onto the contacted NFC tag.<br>
     * The data is written in a way that it is readable by any other android app.
     * @param text the text to be written on the tag.
     * @throws NfcNotWritableException if the contacted tag is set to "Read Only"
     * @throws NfcTextTooLargeException when the data given is too large for the contacted tag.
     * @throws IOException Thrown if connection to nfc tag fails for some reason <b>OR</b> the action is canceled.
     * @throws FormatException If the data on the card is malformed.
     */
    public void write(String text) throws NfcNotWritableException, NfcTextTooLargeException, IOException, FormatException
    {
        if (!this.tag.isWritable())
            throw new NfcNotWritableException();

        NdefMessage msg = new NdefMessage(new NdefRecord[]{
                NdefRecord.createTextRecord("en", text)
        });

        if (msg.getByteArrayLength() > this.tag.getMaxSize())
            throw new NfcTextTooLargeException();

        this.tag.connect();
        this.tag.writeNdefMessage(msg);
        this.tag.close();
    }

    /**
     * Writes string data onto the contacted NFC tag and adds an Android Aplication Record.<br>
     * The data is written in way that only your app can read it.
     * if the app is not open, it will be opened. if the app is not installed,
     * it's "Play Store" page will be opened.
     * @param text the text to be written on the tag.
     * @param context the application context
     * @throws NfcNotWritableException if the contacted tag is set to "Read Only"
     * @throws NfcTextTooLargeException when the data given is too large for the contacted tag.
     * @throws IOException Thrown if connection to nfc tag fails for some reason <b>OR</b> the action is canceled.
     * @throws FormatException If the data on the card is malformed.
     */
    public void write(String text, Context context) throws NfcNotWritableException, NfcTextTooLargeException, IOException, FormatException
    {
        if (!this.tag.isWritable())
            throw new NfcNotWritableException();

        NdefMessage msg = new NdefMessage(new NdefRecord[]{
                NdefRecord.createTextRecord("en", text),
                NdefRecord.createApplicationRecord(context.getApplicationInfo().packageName)
        });

        if (msg.getByteArrayLength() > this.tag.getMaxSize())
            throw new NfcTextTooLargeException();

        this.tag.connect();
        this.tag.writeNdefMessage(msg);
        this.tag.close();
    }

    /**
     * Writes a uri record onto the contacted NFC tag.<br>
     * The data is written in a way that it is readable by any other android app.
     * @param uri the uri to be written on the tag.
     * @throws NfcNotWritableException if the contacted tag is set to "Read Only"
     * @throws NfcTextTooLargeException when the data given is too large for the contacted tag.
     * @throws IOException Thrown if connection to nfc tag fails for some reason <b>OR</b> the action is canceled.
     * @throws FormatException If the data on the card is malformed.
     */
    public void writeUri(String uri) throws NfcNotWritableException, NfcTextTooLargeException, IOException, FormatException
    {
        if (!this.tag.isWritable())
            throw new NfcNotWritableException();

        NdefMessage msg = new NdefMessage(new NdefRecord[]{
                NdefRecord.createUri(uri)
        });

        if (msg.getByteArrayLength() > this.tag.getMaxSize())
            throw new NfcTextTooLargeException();

        this.tag.connect();
        this.tag.writeNdefMessage(msg);
        this.tag.close();
    }

    /**
     * this turns the contacted NFC tag to a "Read Only" tag.
     * this means the data stored on it currently can not be changed in any way.
     * @throws IOException throwm if the card is disconnected mid-action.
     */
    public void makeReadOnly() throws IOException
    {
        this.tag.connect();
        this.tag.makeReadOnly();
        this.tag.close();
    }

    /**
     * Every NFC tag has a unique, unchangeable, hard-coded, ID.
     * This method returns it.
     * @return the unique tag id of the contacted tag.
     */
    public String getTagId()
    {
        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[2];
        for (int i = 0; i < this.tagId.length; i++)
        {
            buffer[0] = Character.forDigit((this.tagId[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(this.tagId[i] & 0x0F, 16);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }
}