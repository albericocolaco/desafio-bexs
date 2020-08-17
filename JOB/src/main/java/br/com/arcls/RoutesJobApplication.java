package br.com.arcls;

import br.com.arcls.entities.response.ResponseFindBestRoute;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RoutesJobApplication {

    public static void main(String... args){
        final Scanner scanner = new Scanner(System.in);

        System.out.print("please enter the route: ");
        final String route = scanner.next();

        final String[] fromTo = route.split("-");
        if(fromTo.length == 2){
            final String from = fromTo[0].toString().trim().toUpperCase();
            final String to = fromTo[1].toString().trim().toUpperCase();

            if(from == null || from.length() == 0 || to == null || to.length() == 0){
                System.out.println("Invalid route");
            } else {
                final String result = restClient(String.format("http://127.0.0.1:8080/api/v1/travel-routes?from=%s&to=%s", from, to));
                final ResponseFindBestRoute response = makeObject(result);
                System.out.println(String.format("best route: %s > %.2f", response.getRouter(), response.getValue()));
            }
        } else {
            System.out.println("Invalid route");
        }

    }

    public static ResponseFindBestRoute makeObject(final String json){
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, ResponseFindBestRoute.class);
        } catch (IOException e) {
            System.out.println("Error to convert json to object");
        }
        return null;
    }

    public static String restClient(final String endPoint){
        String output = null;
        try {
            final URL url = new URL(endPoint);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            final InputStreamReader in = new InputStreamReader(conn.getInputStream());
            final BufferedReader br = new BufferedReader(in);
            output = br.readLine();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
        return output;
    }
}
