package com.rgghgh.nfcactivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;

/**
 * Class used to test Nfc capabilities of the android device
 * @since v1.0
 */
public class NfcTester
{
    private Context context;
    private NfcAdapter adapter;

    public NfcTester(Context context)
    {
        this.context = context;
        this.adapter = NfcAdapter.getDefaultAdapter(context);
    }

    /**
     * <h3>Nfc Utils Method:</h3>
     * <p>Checks if device is Nfc compatible.</p>
     *
     * @return true if compatible, false otherwise.
     * @since v1.0
     */
    public final boolean hasNfc()
    {
        return this.adapter != null;
    }

    /**
     * <h3>Nfc Utils Method:</h3>
     * <p>Checks if app has Nfc Permission enabled.</p>
     *
     * @return true if permission is enabled, false otherwise.
     * @since v1.0
     */
    public final boolean hasNfcPermission()
    {
        PackageManager pm = context.getPackageManager();
        int hasPerm = pm.checkPermission(Manifest.permission.NFC, context.getPackageName());
        return hasPerm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * <h3>Nfc Utils Method:</h3>
     * <p>Checks if phone has Nfc hardware enabled.</p>
     *
     * @return true if hardware is enabled, false otherwise.
     * @since v1.0
     */
    public final boolean isNfcEnabled()
    {
        return this.hasNfc() && this.adapter.isEnabled();
    }
}