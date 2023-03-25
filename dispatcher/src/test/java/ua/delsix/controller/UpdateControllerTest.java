package ua.delsix.controller;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class UpdateControllerTest {
    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "https://api.datamuse.com";

    @Test
    public void whenGetRequest_thenCorrect() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/words?rel_why=funny")
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            assertThat(response.code(), equalTo(200));
        }
    }
}