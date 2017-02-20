import org.json.JSONArray;
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

public class AutoTestSearch{
    @Test
    public void aptTesting() throws Exception {
        try {
            URL url = new URL("https://api.auto.ru/rest?offset=0&category_id=15&geo[region]=1&limit=20offset=0&photo=1&prepend_empty_option=1&sort[set_date]=asc&start_time=1483615723&state=1&sid=e2d0d71afa8c593e_300fc5e4344e778b358ba4e39538af9d&client_tz=180&method=all.sale.search&client_version=3.5.5&key=707e24ee14748fc796bcfb33e149424071b79cebc8537cdf0c24929403f0bc79&client_os=6.0.1&version=2.2.1&uuid=4376274af3d69a17b536913b0cac55e8&device_name=samsung%20SM-G925F&client_platform=android&format=json");
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

            JSONObject full = new JSONObject(entireResponse );
           /* String responseCode = obj.getString("result");
            System.out.println("result : " + responseCode);*/

            JSONObject obj1 = full.getJSONObject("result");
            String totalRes = obj1.getString("total");
            System.out.println("total : " + totalRes);

            JSONArray array=obj1.getJSONArray("sales");

         //JSONObject obj3 = obj2.getJSONObject("poi");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj2 = array.getJSONObject(i);
                JSONObject obj3 = obj2.getJSONObject("poi");
                // JSONObject  placeId = obj3.getJSONObject("region");

                System.out.println("location : " + obj3.getString("region"));

//validating Address as per the requirement

                if (obj3.getString("region").equalsIgnoreCase("Москва")) {
                    System.out.println("Address is as Expected");
                } else {
                    System.out.println("Address is not as Expected");
                }
            }

            conn.disconnect();

        }
        catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}