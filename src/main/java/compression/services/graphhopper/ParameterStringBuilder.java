package compression.services.graphhopper;

import javafx.util.Pair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class ParameterStringBuilder {

    public static String getParamsString(List<Pair<String, String>> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Pair<String, String> entry : params) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}