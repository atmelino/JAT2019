package chapter04.engine;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {

    public static String loadResource(String fileName) throws Exception {
        String result;
        System.out.println(Utils.class.getName());
        System.out.println(Class.forName(Utils.class.getName()));

        try (InputStream in = Class.forName(Utils.class.getName()).getResourceAsStream(fileName);
                Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

}
