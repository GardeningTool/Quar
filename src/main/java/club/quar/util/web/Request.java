package club.quar.util.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public class Request {

    /**
     * The response code will be -1 if it hasn't been set by the connection when Request#connect
     * is called. If the response code is -1 when Request#getResponseCode is called, it will throw
     * an IllegalStateException, signifying that the connection has not yet been made.
     */
    private int responseCode = -1;

    @Getter
    private final URL url;
    @Getter
    private final HttpsURLConnection connection;

    @SneakyThrows
    public Request(String urlString) {
        url = new URL(urlString);
        connection = (HttpsURLConnection) url.openConnection();
    }

    @SneakyThrows
    public Request(URL url) {
        this.url = url;
        this.connection = (HttpsURLConnection) url.openConnection();
    }

    /**
     * Initializes the connection
     */
    @SneakyThrows
    public void connect() {
        connection.setRequestMethod("GET");
        connection.connect();
        responseCode = connection.getResponseCode();
    }

    /**
     * @return The response code of the connection
     * @throws IllegalStateException if the connection has not yet been established
     */
    public int getResponseCode() throws IllegalStateException {
        if (responseCode == -1) {
            throw new IllegalStateException("A connection has not yet been made yet!");
        }

        return responseCode;
    }

    /**
     * @return A JsonArray  object of the response
     * @throws IllegalStateException If the connection has not yet been made
     * @throws IOException           If the BufferedReader fails to read the response
     */
    public JsonArray toJsonArray() throws IllegalStateException, IOException {
        String content = getRawContent();
        Gson gson = new Gson();

        return gson.fromJson(content, JsonArray.class);
    }

    /**
     * @return A Map object of the response
     * @throws IllegalStateException If the connection has not yet been made
     * @throws IOException           If the BufferedReader fails to read the response
     */
    public Map<?, ?> map() throws IllegalStateException, IOException {
        String content = getRawContent();
        Gson gson = new Gson();

        return gson.fromJson(content, Map.class);
    }

    /**
     * @return The response from NameMC, as a String
     * @throws IllegalStateException If the connection has not yet been made
     * @throws IOException           If the BufferedReader fails to read the response
     */
    private String getRawContent() throws IllegalStateException, IOException {
        if (responseCode == -1) {
            throw new IllegalStateException("A connection has not been made yet!");
        }

        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        return builder.toString();
    }
}