package compression.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WebResponse {
    @Getter
    private int statusCode;
    @Getter
    private String responseString;
}
