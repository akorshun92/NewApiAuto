import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by korshun on 1/5/2017.
 */

public class AutoStart {

    public static final String ACCESS_KEY = "";
    //  public static final String BASE_URL = "https://api.auto.ru/rest/?sid=&client_tz=180&method=api.service.getUuid&client_version=3.10.1&key=1d2b14555a83699f57fd77d17aa2d5ce9431cd7d9f3edea14186b044e76b606a&client_os=6.0.1&version=2.2.2&device_name=motorola%20XT1562&client_platform=android&format=json";
    public static JSONObject json;
    public static String sid;
    public static String uuid_header;
    public static String uuid;
    public static String autoruuid;
    public static String x_auth;
    public static String auth_sid;
    public static String auth_sid_key;
    public static String auth_autoruuid;
    public static long millis = System.currentTimeMillis();
    public static String url_api2 = "https://api2.auto.ru/1.1/search?category_id=15&page_num=1&page_size=50&creation_date_to=" + millis;
    String username = "yuioru@yandex.ru";
    String password = "111111";

    static CloseableHttpClient client = HttpClients.createDefault();


    @BeforeClass
    public static void beforeClass() throws IOException, JSONException {
        json = FirstConnectJson.json();
        sid = String.valueOf(json.get("sid"));
        uuid_header = "OAuth" + " " + String.valueOf(json.getJSONObject("result").get("uuid"));
        uuid = String.valueOf(json.getJSONObject("result").get("uuid"));
        x_auth = "Vertis" + " " + String.valueOf(json.getJSONObject("result").get("uuid")) + " " + "5c27f9e8-2b90-433e-a0bf-b5222bbd97d0";
        autoruuid = String.valueOf(json.get("autoruuid"));
    }


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

            System.out.println("Response : " + entireResponse);

            scan.close();

            JSONObject obj = new JSONObject(entireResponse);
            /*String responseCode = obj.getString("status");
            System.out.println("status : " + responseCode);*/

            JSONObject full = new JSONObject(entireResponse);
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

    @Test //Авторизация пользователя
    public void autorize() {
        RestAssured.baseURI = "https://api.auto.ru";
        Response r = given().headers("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8").
                body("login=" + username + "&pass=" + password + "&sid=" + sid + "&method=users.auth.login&key=1d2b14555a83699f57fd77d17aa2d5ce9431cd7d9f3edea14186b044e76b606a" + "&version=2.2.2&uuid=" + uuid + "&format=json").
                when().post("/rest/");
        assertTrue(r.statusCode() == 200);
        // System.out.println(r.body().asString());
        auth_sid = r.body().jsonPath().get("sid");
        auth_sid_key = r.body().jsonPath().get("sid_key");
        auth_autoruuid = r.body().jsonPath().get("autoruuid");
    }

    @Test //Ошибка логина
    public void badautorize() {
        String username = "yuioru123@yandex.ru";

        RestAssured.baseURI = "https://api.auto.ru";
        Response r = given().headers("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8").
                body("login=" + username + "&pass=" + password + "&sid=" + sid + "&method=users.auth.login&key=1d2b14555a83699f57fd77d17aa2d5ce9431cd7d9f3edea14186b044e76b606a" + "&version=2.2.2&uuid=" + uuid + "&format=json").
                when().post("/rest/");
        Object message = r.body().jsonPath().get("error.message");
        //String error = r.body("error", ).jsonPath().get("error");
        //Response message = r.body(error).jsonPath("message");
        System.out.print(message);
        assertTrue(r.statusCode() == 200);
        assertTrue(message.equals("Неверный логин или пароль."));
        // System.out.println(r.body().asString());
        auth_sid = r.body().jsonPath().get("sid");
        auth_sid_key = r.body().jsonPath().get("sid_key");
        auth_autoruuid = r.body().jsonPath().get("autoruuid");
        System.out.println(r.body().asString());
    }
}