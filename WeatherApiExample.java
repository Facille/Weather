package Weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApiExample {
    public static void main(String[] args) {
        String accessKey = "15d20285-68a0-40ff-b4e1-981fc172966f";
        double lat = 55.75396;
        double lon = 37.620393;
        String lang = "ru";
        int limit = 7;

        try {

            String urlString = String.format("https://api.weather.yandex.ru/v2/forecast?lat=55.75&lon=37.62&limit=7", lat, lon, lang);
            System.out.println("Request URL: " + urlString);
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Yandex-Weather-Key", accessKey);


            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Response: " + response.toString());

                JSONObject jsonResponse = new JSONObject(response.toString());
                // Обработка ответа
                System.out.println("Weather data: " + jsonResponse.toString());
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();
                System.out.println("Error: " + responseCode + " - " + errorResponse.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}