package br.com.arcls.routes.jobs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TravelRoutesJob{

    @Value("${input.data}")
    private static String inputRoutesData;

    public static void main(String... args) throws Exception {

        System.out.println(inputRoutesData);
    }
}
