package design_java_rest.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class RESTJsonUtil {

    public static final Gson GSON = new Gson();
    public static final Gson GSONDate = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();

    /**
     * Parse input stream to string, UTF-8.
     * @param in
     * @return
     * @throws IOException
     */

    public static String parseStringInputStream(InputStream in)throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
        while((line = br.readLine()) != null){
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
