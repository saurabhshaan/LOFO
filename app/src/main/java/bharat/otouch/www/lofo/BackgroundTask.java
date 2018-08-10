package bharat.otouch.www.lofo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by Adboss on 11/22/2016.
 */


public class BackgroundTask extends AsyncTask<String,Void,String> {

    Context ctx;

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url = "http://172.28.172.2/lostandfound/postitems.php";
        Log.d("TAG", "step1");


        String method = params[0];
        if (method.equals("register")) {
            String Product_name= params[1];
            String Mobile = params[2];
            String Email = params[3];
            String Product_img=params[4];
            Log.d("TAG",Product_name+" "+Mobile+" "+Email+" "+Product_img);
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                Log.d("TAG", "open url connnection ");
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //encode data before send it
                //no space permiteted in equals sign
                String data = URLEncoder.encode("Product_name", "UTF-8") + "=" + URLEncoder.encode(Product_name, "UTF-8") + "&" +
                        URLEncoder.encode("Mobile", "UTF-8") + "=" + URLEncoder.encode(Mobile, "UTF-8") + "&" +
                        URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&" +
                        URLEncoder.encode("Product_img", "UTF-8") + "=" + URLEncoder.encode(Product_img, "UTF-8");
                Log.d("TAG", "data parameter set ");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                Log.d("TAG", "buffer writer close");
                os.close();
                //get response from server
                InputStream is = httpURLConnection.getInputStream();
                Log.d("TAG", "debug");
                is.close();
                return "registration.....success";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);


        if (result.equals("SuccessfullRegistration")) {
            Toast.makeText(ctx, "Registration Success", Toast.LENGTH_SHORT).show();


        }
    }
}