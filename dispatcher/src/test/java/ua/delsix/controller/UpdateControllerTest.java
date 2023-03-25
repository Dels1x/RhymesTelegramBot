package ua.delsix.controller;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class UpdateControllerTest {
    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "https://api.datamuse.com";
    private static String word = "dick";

    @Test
    public void whenGetRequest_thenCorrect() throws IOException{
        Request perfectRhymesRequest = new Request.Builder()
                .url(String.format("%s/words?rel_rhy=%s", BASE_URL, word))
                .build();
        Request nearRhymesRequest = new Request.Builder()
                .url(String.format("%s/words?rel_nry=%s", BASE_URL, word))
                .build();


        Call perfectRhymesCall = client.newCall(perfectRhymesRequest);
        Call nearRhymesCall = client.newCall(nearRhymesRequest);
        Response perfectRhymesResponse = perfectRhymesCall.execute();
        Response nearRhymesResponse = nearRhymesCall.execute();

        if (perfectRhymesResponse.code() == 200) {
            String perfectRhymesResponseBody = perfectRhymesResponse.body().string();
            String nearRhymesResponseBody = nearRhymesResponse.body().string();

            JSONArray perfectRhymesArray = new JSONArray(perfectRhymesResponseBody);
            JSONArray nearRhymesArray = new JSONArray(nearRhymesResponseBody);

            for (int i = 0; i < perfectRhymesArray.length(); i++) {
                String rhymeWord = perfectRhymesArray.getJSONObject(i).getString("word");
                System.out.println(rhymeWord);
            }

            for (int i = 0; i < nearRhymesArray.length(); i++) {
                String rhymeWord = nearRhymesArray.getJSONObject(i).getString("word");
                System.out.println(rhymeWord);
            }
        }
    }
}