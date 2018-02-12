package compression.services.graphhopper;

import compression.model.web.WebResponse;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public abstract class BaseService {
    protected final String baseAddress;
    protected int connectionTimeout = 5000;
    protected int readTimeout = 5000;

    protected BaseService(String baseAddress){
        this.baseAddress = baseAddress;
    }

    protected WebResponse sendRequest(List<Pair<String, String>> parameters){
        try {
            String params = ParameterStringBuilder.getParamsString(parameters);
            String urlString = baseAddress+"?"+params;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);

            int responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return new WebResponse(responseCode, content.toString());
        } catch (Exception e) {
            throw new WebServiceExcpetion("Web services call failed.", e);
        }
    }
}
