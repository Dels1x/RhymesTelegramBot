package ua.delsix.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONException;
import ua.delsix.service.DatamuseService;

import java.io.IOException;
import java.util.List;

public class DatamuseServiceImpl implements DatamuseService {
    private static final String BASE_URL = "https://api.datamuse.com";
    private static final String PERFECT_RHYMES_RELATION = "rel_rhy";
    private static final String NEAR_RHYMES_RELATION = "rel_nry";
    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public List<String> getPerfectRhymes(String word) throws IOException, JSONException {
        return null;
    }

    @Override
    public List<String> getNearRhymes(String word) throws IOException, JSONException {
        return null;
    }

    @Override
    public List<String> executeRequest(Request request) throws IOException, JSONException {
        return null;
    }
}
