package ua.delsix.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONException;
import ua.delsix.service.DatamuseService;

import java.io.IOException;
import java.util.List;

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
        return null;
    }
}
