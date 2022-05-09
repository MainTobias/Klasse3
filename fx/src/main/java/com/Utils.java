package com;

import java.net.URL;

public class Utils {
    public static URL getPath(Class<?> c) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1-$2";
        return c.getResource(c.getSimpleName().replaceAll("(?i)app(?:lication)", "").replaceAll(regex, replacement).toLowerCase() + ".fxml");
    }
}
