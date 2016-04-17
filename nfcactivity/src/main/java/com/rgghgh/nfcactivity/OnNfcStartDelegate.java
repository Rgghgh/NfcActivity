package com.rgghgh.nfcactivity;

public interface OnNfcStartDelegate
{
    /**
     * <p>Invoked on Nfc tag contacted, <b>before</b> any data is tranfered.</p>
     * <p>Actions can be performed on the Nfc Tag using the NfcConnnection param.</p>
     *
     * @param conn NfcConnection with the tag contacted.
     * @see NfcConnection
     * @return <i>(Optional)</i> true is any action was taken. false otherwise. Used mainly for OOP reasons.
     * @since v1.0
     */
    boolean onNfcStart(NfcConnection conn);
}
