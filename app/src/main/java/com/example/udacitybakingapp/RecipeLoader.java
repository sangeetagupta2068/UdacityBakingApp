package com.example.udacitybakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;

public class RecipeLoader extends AsyncTaskLoader {

    private final String mUrl;
    private SharedPreferences mPreference;

    public RecipeLoader(Context context, String url, SharedPreferences preferences) {
        super(context);
        mUrl = url;
        mPreference = preferences;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return JSONFormatter.fetchRecipeData(mUrl, mPreference);
    }
}
