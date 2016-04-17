package com.rgghgh.nfcactivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;

/**
 * Extend this class in your activity to implement NFC communication functionality.
 * @since v1.0
 */
public abstract class NfcActivity extends Activity
{
    private NfcAdapter adapter;
    private NfcTester tester;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.adapter = NfcAdapter.getDefaultAdapter(this);
        this.tester = new NfcTester(this);
        this.pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndefFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        ndefFilter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);

        try
        {
            ndefFilter.addDataType("*/*");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.intentFiltersArray = new IntentFilter[]{ndefFilter};
        this.techListsArray = new String[][]{
                new String[]{
                        NfcA.class.getName(),
                        Ndef.class.getName(),
                        MifareUltralight.class.getName()
                },
                new String[]{
                        NfcA.class.getName()
                },
                new String[]{
                        Ndef.class.getName()
                },
                new String[]{
                        MifareUltralight.class.getName()
                }
        };
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (hasNfc())
            this.adapter.disableForegroundDispatch(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (hasNfc())
            this.adapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tagFromIntent != null)
        {
            NfcConnection conn = new NfcConnection(tagFromIntent);
            onNfcStart(conn);
        }
    }

    /**
     * <p>Invoked on Nfc tag contacted, <b>before</b> any data is tranfered.</p>
     * <p>Actions can be performed on the Nfc Tag using the NfcConnnection param.</p>
     * @param conn NfcConnection with the tag contacted.
     * @see NfcConnection
     * @since v1.0
     */
    public abstract void onNfcStart(NfcConnection conn);

    /**
     * <p>Checks if device is Nfc compatible.</p>
     * @return true if compatible, false otherwise.
     * @since v1.0
     */
    public final boolean hasNfc()
    {
        return this.tester.hasNfc();
    }

    /**
     * <p>Checks if app has Nfc Permission enabled.</p>
     * @return true if permission is enabled, false otherwise.
     * @see NfcTester
     * @since v1.0
     */
    public final boolean hasNfcPermission()
    {
        return this.tester.hasNfcPermission();
    }

    /**
     * <p>Checks if phone has Nfc hardware enabled.</p>
     * @return true if hardware is enabled, false otherwise.
     * @see NfcTester
     * @since v1.0
     */
    public final boolean isNfcEnabled()
    {
        return this.tester.isNfcEnabled();
    }
}