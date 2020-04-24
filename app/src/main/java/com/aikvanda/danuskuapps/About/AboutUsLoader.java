package com.aikvanda.danuskuapps.About;

/**
 * Created by Satria on 4/25/2018.
 */
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class AboutUsLoader extends AsyncTaskLoader<List<AboutUs>> {

    /** Tag for log messages */
    private static final String LOG_TAG = AboutUsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public AboutUsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<AboutUs> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<AboutUs> aboutus= QueryUtils.fetchEarthquakeData(mUrl);
        return aboutus;
    }
}
