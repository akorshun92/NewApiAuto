/**
 * Created by korshun on 2/20/2017.
 */
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

    public class FirstConnectJson {
        public static final String BASE_URL = "https://api.auto.ru/rest/?sid=&client_tz=180&method=api.service.getUuid&client_version=3.10.1&key=1d2b14555a83699f57fd77d17aa2d5ce9431cd7d9f3edea14186b044e76b606a&client_os=6.0.1&version=2.2.2&device_name=motorola%20XT1562&client_platform=android&format=json";
        static CloseableHttpClient client = HttpClients.createDefault();

        public static JSONObject json() throws IOException, JSONException {
            HttpGet get = new HttpGet(BASE_URL);
            CloseableHttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            JSONObject json = new JSONObject(EntityUtils.toString(entity));
            return json;
        }
    }

