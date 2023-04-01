package ua.delsix.service;

import okhttp3.Request;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface DatamuseService {
    public List<String> getPerfectRhymes(String word) throws IOException, JSONException;
    public List<String> getNearRhymes(String word) throws IOException, JSONException;
    public List<String> executeRequest(Request request) throws IOException, JSONException;
}
