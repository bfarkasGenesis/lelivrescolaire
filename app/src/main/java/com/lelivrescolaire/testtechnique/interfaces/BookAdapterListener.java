package com.lelivrescolaire.testtechnique.interfaces;

/**
 * Created by root on 3/31/16.
 * Listener between the adapter and the activityx
 */
public interface BookAdapterListener {

    /**
     * Retrieve the id of the selected book
     *
     * @param idBook book's id
     */
    void onBookSelected(long idBook);
}
