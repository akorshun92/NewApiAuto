import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by korshun on 1/5/2017.
 */

public class AutoStart{
    @Test
    public void aptTesting() throws Exception {
        try {
            URL url = new URL("https://api.auto.ru/rest/?sid=&method=api.service.getUuid&client_tz=120&client_version=3.11.0&key=b7bf0dfc8cc562c1bf2cffdd9e78fc181f97f6c82f85fbca16d62d3d3258963c&client_os=7.1.1&version=2.2.2&device_name=LGE%20Nexus%205X&client_platform=android&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code:"
                        + conn.getResponseCode());
            }

            Scanner scan = new Scanner(url.openStream());
            String entireResponse = new String();
            while (scan.hasNext())
                entireResponse += scan.nextLine();

            System.out.println("Response : "+entireResponse);

            scan.close();

            JSONObject obj = new JSONObject(entireResponse );
            /*String responseCode = obj.getString("status");
            System.out.println("status : " + responseCode);*/

            JSONObject full = new JSONObject(entireResponse );
            JSONObject obj1 = full.getJSONObject("result");
            String totalRes = obj1.getString("uuid");
            //System.out.println("uuid : " + totalRes);
            String uuid = obj1.getString("uuid");
            String key = "5c27f9e8-2b90-433e-a0bf-b5222bbd97d0";
            String xauth = "X-Authorization: Vertis " + uuid + " " + key;
            String secauth = "Authorization: OAuth " + uuid;

            System.out.println(xauth);
            System.out.println(secauth);




            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}