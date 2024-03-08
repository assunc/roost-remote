import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.json.*;

public class DBTest {

    public String makeGETRequest(String urlName){
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        try {
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null)
            {
                sb.append(line + '\n');
            }
            conn.disconnect();
            return sb.toString();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (ProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "";

    }

    public String parseJSONLogin(String jsonString){
        String password = "";

        try {
            JSONArray array = new JSONArray(jsonString);
            JSONObject curObject = array.getJSONObject(0);
            password = curObject.getString("password");

        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return password;
    }

    public String parseJSONUserID(String jsonString){
        String id="";

        try {
            JSONArray array = new JSONArray(jsonString);
            JSONObject curObject = array.getJSONObject(0);
            id = Integer.toString(curObject.getInt("idUser"));


        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return id;
    }


    public String parseJSONCoopID(String jsonString){
        String id="";

        try {
            JSONArray array = new JSONArray(jsonString);
            JSONObject curObject = array.getJSONObject(0);
            id = Integer.toString(curObject.getInt("idCoop"));

        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return id;
    }
    public static void main(String[] args) {
        DBTest rc = new DBTest();
        String response = rc.makeGETRequest("https://studev.groept.be/api/a21ib2demo/all" );
        rc.parseJSONLogin(response);
    }
}

//easteregg
