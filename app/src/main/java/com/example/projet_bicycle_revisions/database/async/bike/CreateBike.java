package com.example.projet_bicycle_revisions.database.async.bike;

import android.app.Application;
import android.os.AsyncTask;

import com.example.projet_bicycle_revisions.BaseApp;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;

public class CreateBike extends AsyncTask<BikesEntity,Void,Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateBike(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(BikesEntity... params) {
        try {
            for (BikesEntity bike : params)
                ((BaseApp) application).getDatabase().bikeDao()
                        .insert(bike);
        } catch (Exception e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void avoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
