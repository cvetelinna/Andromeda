package com.example.andromedaf95243xf102156;

import static com.example.andromedaf95243xf102156.Secrets.API_TOKEN;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HoroscopeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String sunSign = "Aries";
    TextView resultView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope);
        Button buttonView = findViewById(R.id.button);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        getPredictions(buttonView);
                    }
                });
            }
        });
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sunsigns, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        resultView = findViewById(R.id.resultView);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        sunSign = "Aries";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sunSign = parent.getItemAtPosition(position).toString();
    }
    public void getPredictions(View view) {
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return callAztroAPI("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=" + sunSign + "&day=today");
            }

            @Override
            protected void onPostExecute(String result) {
                onResponse(result);
            }
        };

            asyncTask.execute();
    }

    private String callAztroAPI(String apiUrl) {
        String result = "";
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            // set headers for the request
            // set host name
            connection.setRequestProperty("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com");
            // set the rapid-api key
            connection.setRequestProperty("x-rapidapi-key", API_TOKEN);
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // set the request method - POST
            connection.setRequestMethod("POST");
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            // read the response data
            int data = reader.read();
            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void onResponse(String result) {
        try {
            // convert the string to JSON object for better reading
            JSONObject resultJson = new JSONObject(result);
            // Initialize prediction text
            String prediction = "Today's prediction\n";
            prediction += this.sunSign+"\n";
            // Update text with various fields from response
            prediction += resultJson.getString("date_range")+"\n\n";
            prediction += resultJson.getString("description");
            //Update the prediction to the view
            setText(this.resultView,prediction);
        } catch (Exception e) {
            e.printStackTrace();
            this.resultView.setText("Oops!! something went wrong, please try again");
        }
    }

    private void setText(final TextView text, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

}
