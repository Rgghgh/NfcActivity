package com.rgghgh.nfcactivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Extend this in your activity to implement Android Beam functionality.
 * @since v1.0
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public abstract class BeamActivity extends AppCompatActivity implements CreateNdefMessageCallback
{
    private NfcAdapter adapter;
    private boolean bound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.adapter = NfcAdapter.getDefaultAdapter(this);
        this.adapter.setNdefPushMessageCallback(this, this);
        this.bound = false;
    }

    /**
     * Called on android beam contact.
     * Physically when two phones touch.
     * This method determines what data should be sent from this device to the other.
     * Data will be sent from this device if on cantact, when the beam animation is shown,
     * the user of the current device clicks the animation screen.
     * @return data to be passed to the contacted phone, should the user will invoke the beam from this phone.
     * @since v1.0
     */
    public abstract String onBeamSend();

    /**
     * Called on android beam data received.
     * @param data data message from the android beam device.
     * @since v1.0
     */
    public abstract void onBeamReceived(String data);

    @Override
    public final NdefMessage createNdefMessage(NfcEvent event)
    {
        if (this.bound)
            return new NdefMessage(new NdefRecord[]{
                    NdefRecord.createTextRecord("en", onBeamSend()),
                    NdefRecord.createApplicationRecord(getApplicationInfo().packageName)
            });
        return new NdefMessage(new NdefRecord[]{
                NdefRecord.createTextRecord("en", onBeamSend())
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()))
        {
            Intent intent = getIntent();
            NdefMessage msg = (NdefMessage) intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)[0];
            String data = new String(msg.getRecords()[0].getPayload());
            onBeamReceived(data);
        }
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        setIntent(intent);
    }

    /**
     * Manually invoke Android Beam to share data.
     * @see NfcAdapter's invokeBeam();
     * @since v1.0
     */
    public void invokeBeam()
    {
        this.adapter.invokeBeam(this);
    }

    /**
     * Beams bound to app will be read only by this app. if it not open, it will be.
     * if it is not installed, the app's page on the google play store will be opened.
     * @return true if the future beams sent will be bound to the app. false otherwise.
     * @since v1.0
     */
    public final boolean isBoundToApp()
    {
        return this.bound;
    }

    /**
     * Sets the beam bound to app state.
     * Beams bound to app will be read only by this app. if it not open, it will be.
     * if it is not installed, the app's page on the google play store will be opened.
     * @param state bind to app.
     * @since v1.0
     */
    public final void setBoundToApp(boolean state)
    {
        this.bound = state;
    }

}