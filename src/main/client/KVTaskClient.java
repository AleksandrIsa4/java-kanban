package main.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String url;
    private String apiToken;

    public KVTaskClient() {
        url = null;
        apiToken = null;
    }

    public void setUrl(String url) {
        this.url = url;
        apiToken = register(url);
    }

    private String register(String url) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> send = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
            return send.body();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void put(String key, String json) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(url + "save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String load(String key) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(url + "load/" + key + "?API_TOKEN=" + apiToken))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
            JsonElement jsonElement = JsonParser.parseString(response.body());
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            return jsonArray.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
