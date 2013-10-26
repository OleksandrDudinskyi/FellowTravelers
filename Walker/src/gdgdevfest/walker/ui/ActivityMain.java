package gdgdevfest.walker.ui;

import gdgdevfest.walker.R;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ActivityMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
    private void getCurrentLocation() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location lastKnownLocation;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        EditText currentLocation = (EditText) findViewById(R.id.currentLocation);
        currentLocation.setText(getAddressGoogleQuery(lastKnownLocation.getAltitude(), lastKnownLocation.getLatitude()));
    }
    private String getAddressGoogleQuery(Double latitude, Double longitude) {
        String device_address = "";
        try {
            String URL = "http://maps.google.com/maps/geo?q=" + latitude + ","
                    + longitude + "&output=csv";

              device_address = Get(URL);
//            String[] output = device_address.split("\"");
        } catch (Exception e) {
        }
        return device_address;
    }

    public static String Get(String URLStr) throws Exception
    {


        String resultStr = "";
        BufferedReader in = null;
        try
        {

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(URLStr);
            HttpResponse response = client.execute(request);
            in = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            resultStr = sb.toString();
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();

                } catch (Exception e)
                {
                }
            }

        }
        return resultStr;
    }
}