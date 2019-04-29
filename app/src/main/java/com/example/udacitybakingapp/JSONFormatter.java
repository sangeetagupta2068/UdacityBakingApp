package com.example.udacitybakingapp;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.udacitybakingapp.models.Ingredient;
import com.example.udacitybakingapp.models.Recipe;
import com.example.udacitybakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class JSONFormatter {

    private static final String TAG = JSONFormatter.class.getSimpleName();
    private static final String THEJSON = "theJson";
    public static String JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static SharedPreferences mPreferences;

    public static List<Recipe> fetchRecipeData(String requestURL, SharedPreferences preferences) {
        URL url = createUrl(JSON_URL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPreferences = preferences;
        mPreferences.edit().putString(THEJSON, jsonResponse).apply();

        return extractDataFromJson();
    }

    private static URL createUrl(String requestURL) {
        URL mUrl;

        try {
            mUrl = new URL(requestURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return mUrl;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;

        if (url == null) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.v(TAG, " " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder outputStream = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                outputStream.append(line);
                line = reader.readLine();
            }
        }
        return outputStream.toString();
    }


    public static ArrayList<Recipe> extractDataFromJson() {
        String json = mPreferences.getString(THEJSON, "");
        int id;
        String name;
        int servings;
        ArrayList<Recipe> mRecipe = new ArrayList<>();
        try {
            JSONArray baseJsonArray = new JSONArray(json);
            for (int i = 0; i < baseJsonArray.length(); i++) {
                JSONObject currentRecipe = baseJsonArray.getJSONObject(i);
                id = currentRecipe.getInt("id");
                name = currentRecipe.getString("name");
                servings = currentRecipe.getInt("servings");
                mRecipe.add(new Recipe(id, name, servings));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mRecipe;
    }

    public static ArrayList<Ingredient> extractIngredientsFromJson(int mId) {
        String json = mPreferences.getString(THEJSON, "");
        int id;
        int quantity;
        ArrayList<Ingredient> mIngredients = new ArrayList<>();
        try {
            JSONArray baseJsonArray = new JSONArray(json);
            for (int i = 0; i < baseJsonArray.length(); i++) {
                JSONObject currentRecipe = baseJsonArray.getJSONObject(i);
                id = currentRecipe.getInt("id");
                if (mId == id) {
                    JSONArray ingredientsArray = currentRecipe.getJSONArray("ingredients");
                    for (int j = 0; j < ingredientsArray.length(); j++) {
                        JSONObject currentIngredient = ingredientsArray.getJSONObject(j);
                        quantity = currentIngredient.getInt("quantity");
                        String measure = currentIngredient.getString("measure");
                        String ingredient = currentIngredient.getString("ingredient");
                        mIngredients.add(new Ingredient(quantity, measure, ingredient));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mIngredients;
    }

    public static ArrayList<Step> extractStepsFromJson(int mId) {
        String json = mPreferences.getString(THEJSON, "");
        int id;
        ArrayList<Step> mSteps = new ArrayList<>();
        try {
            JSONArray baseJsonArray = new JSONArray(json);
            for (int i = 0; i < baseJsonArray.length(); i++) {
                JSONObject currentRecipe = baseJsonArray.getJSONObject(i);
                id = currentRecipe.getInt("id");
                if (mId == id) {
                    JSONArray stepsArray = currentRecipe.getJSONArray("steps");
                    for (int j = 0; j < stepsArray.length(); j++) {
                        JSONObject currentStep = stepsArray.getJSONObject(j);
                        int stepId = currentStep.getInt("id");
                        Log.d(TAG, "StepId: " + stepId);
                        String sDescription = currentStep.getString("shortDescription");
                        String description = currentStep.getString("description");
                        String videoUrl = currentStep.getString("videoURL");
                        String thumbnail = currentStep.getString("thumbnailURL");
                        mSteps.add(new Step(stepId, sDescription, description, videoUrl, thumbnail));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mSteps;
    }
}
