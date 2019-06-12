package org.acme.config;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Inject
    Config config;

    @Test
    void testBimBam() throws InterruptedException {

        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String value = config.getValue("greeting.name", String.class);
            System.out.println(Thread.currentThread().getId() + " val in test: " + value);
            names.add(value);
            TimeUnit.MILLISECONDS.sleep(50);
        }
        System.out.println(names);
        Assertions.assertTrue(names.contains("Bim"));
        Assertions.assertTrue(names.contains("Bam"));


    }


}
