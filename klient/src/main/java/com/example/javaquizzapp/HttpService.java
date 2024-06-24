package com.example.javaquizzapp;

import com.example.javaquizzapp.entity.Question;
import com.example.javaquizzapp.entity.Student;
import com.example.javaquizzapp.entity.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class HttpService {
    private final URL apiUrl;
    private final Gson gson;


    public HttpService() throws MalformedURLException {
        this.apiUrl = new URL("http://localhost:8080/api");
        this.gson = new Gson();
    }
    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public void addQuestion(Question question) throws IOException, InterruptedException {
        String url = apiUrl + "/questions";
        String json = gson.toJson(question);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to add question: " + response.body());
        }
    }

    public List<Student> findAllStudents() throws IOException {
        String endpoint = "/students";
        String response = sendGetRequest(endpoint);
        Type studentListType = new TypeToken<List<Student>>() {}.getType();
        return gson.fromJson(response, studentListType);
    }
    public Student getCurrentStudent() throws IOException {
        String endpoint = "/current-student";
        String response = sendGetRequest(endpoint);
        return gson.fromJson(response, Student.class);
    }

    public void clearCurrentStudent() throws IOException {
        String endpoint = "/current-student/logout";
        sendPostRequest(endpoint, "");
    }
    public Student findByIndex(String index) throws IOException {
        String endpoint = "/students/search/findByIndex?index=" + index;
        String response = sendGetRequest(endpoint);
        return gson.fromJson(response, Student.class);
    }

    private String sendGetRequest(String endpoint) throws IOException {
        URL url = new URL(apiUrl + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } else {
            throw new IOException("GET request not worked, response code: " + responseCode);
        }
    }

    private String sendPostRequest(String endpoint, String jsonInputString) throws IOException {
        URL url = new URL(apiUrl + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Odczytaj pełną odpowiedź serwera
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Response: " + response.toString());
        }

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("POST request not worked, response code: " + responseCode);
        }
        return endpoint;
    }
    public void registerStudent(Student student) throws IOException {
        String endpoint = "/students/register";
        String jsonInputString = gson.toJson(student);
        sendPostRequest(endpoint, jsonInputString);
    }
    public List<Test> findAllTests() throws IOException {
        String endpoint = "/tests";
        String response = sendGetRequest(endpoint);
        Type testListType = new TypeToken<List<Test>>() {}.getType();
        return gson.fromJson(response, testListType);
    }

    public List<Test> findTestsByStudent(Long studentId) throws IOException {
        String endpoint = "/tests/student/" + studentId;
        String response = sendGetRequest(endpoint);
        Type testListType = new TypeToken<List<Test>>() {}.getType();
        return gson.fromJson(response, testListType);
    }

    public void saveTest(Test test) throws IOException {
        String endpoint = "/tests";
        String jsonInputString = gson.toJson(test);
        System.out.println(jsonInputString);
        String response = sendPostRequest(endpoint, jsonInputString);

//        gson.fromJson(response, Test.class);
    }
    public List<Question> getAllQuestionsWithAnswers() throws IOException {
        String endpoint = "/questions";
        String response = sendGetRequest(endpoint);
        Type questionListType = new TypeToken<List<Question>>() {}.getType();
        return gson.fromJson(response, questionListType);
    }

    public List<Question> getRandomQuestions(int numberOfQuestions) throws IOException {
        String endpoint = "/questions/random?number=" + numberOfQuestions;
        String response = sendGetRequest(endpoint);
        Type questionListType = new TypeToken<List<Question>>() {}.getType();
        return gson.fromJson(response, questionListType);
    }

    public boolean validateStudent(String index, String password) throws IOException {
        String endpoint = "/students/login?index=" + index + "&password=" + password;
        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl + endpoint).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                Student currentStudent = gson.fromJson(response.toString(), Student.class);
                setCurrentStudent(currentStudent);
                return true;
            }
        } else {
            System.out.println("POST request not worked, response code: " + responseCode);
            return false;
        }
    }

    @Setter
    private Student currentStudent;

    public Student getCurrentStudentFromService() {
        return currentStudent;
    }

    public void clearCurrentStudentFromService() {
        this.currentStudent = null;
    }
}
