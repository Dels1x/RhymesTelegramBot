package ua.delsix.service.impl;

import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import ua.delsix.service.DatamuseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class DatamuseServiceImpl implements DatamuseService {
    private static final String BASE_URL = "https://api.datamuse.com";
    private static final String PERFECT_RHYMES_RELATION = "words?rel_rhy";
    private static final String NEAR_RHYMES_RELATION = "words?rel_nry";
    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public List<String> getPerfectRhymes(String word) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(String.format("%s/%s=%s", BASE_URL, PERFECT_RHYMES_RELATION, word))
                .build();

        return executeRequest(request);
    }

    @Override
    public List<String> getNearRhymes(String word) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(String.format("%s/%s=%s", BASE_URL, NEAR_RHYMES_RELATION, word))
                .build();

        return executeRequest(request);
    }

    @Override
    public List<String> executeRequest(Request request) throws IOException, JSONException {
        List<String> words = new ArrayList<>();

        Call call = client.newCall(request);
        Response response = call.execute();

        if (response.code() == 200) {
            String responseBody = response.body().string();
            JSONArray wordsJSON = new JSONArray(responseBody);

            for(int i = 0; i < wordsJSON.length(); i++) {
                String rhymeWord = wordsJSON.getJSONObject(i).getString("word");
                words.add(rhymeWord);
            }


        } else {
           log.error("Response code: {}", response.code());
        }

        return words;
    }
}
