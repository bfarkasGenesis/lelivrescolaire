package com.lelivrescolaire.testtechnique.interfaces;

/**
 * Created by root on 3/31/16.
 * Listener between the adapter and the activity
 */
public interface PageAdapterListener {

    /**
     * Retrieve the id of the selected page
     * Trigered when the itemview is selected
     *
     * @param idPage
     */
    void onPageSelected(long idPage);
}
