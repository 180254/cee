package pl.lodz.p.cee.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class HandlerHelper {

    public String resource(String name) throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/" + name);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }
}
