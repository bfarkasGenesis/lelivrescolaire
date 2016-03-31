package com.lelivrescolaire.testtechnique.interfaces;

/**
 * Created by root on 3/31/16.
 * Listener for the init asynctak and the PageActivity
 */
public interface WebViewTaskListener {

    /**
     * Called when the async task is over
     */
    void onLoadFinished(String result);

}
