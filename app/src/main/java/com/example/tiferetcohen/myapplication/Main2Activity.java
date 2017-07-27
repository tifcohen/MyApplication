package com.example.tiferetcohen.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ArrayList<Contacts> arrayList;
    TextView id;
    TextView name;
    TextView email;
    TextView address;
    TextView gender;
    RelativeLayout relativeLayout;
    int currentIndex;
    ImageView image;

    private static String url = "https://api.androidhive.info/contacts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       // arrayList = loadJSONFromAssest();
        arrayList = new ArrayList<>();

        new GetContacts().execute();

        relativeLayout = (RelativeLayout) findViewById(R.id.container);
        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        address = (TextView) findViewById(R.id.address);
        gender = (TextView) findViewById(R.id.gender);
        image = (ImageView) findViewById(R.id.image);
        currentIndex = 0;

        String url = "http://www.gettyimages.com/gi-resources/images/Embed/new/embed2.jpg";
        UrlImageViewHelper.setUrlDrawable(image, url, R.mipmap.ic_launcher_round);

        relativeLayout.setOnTouchListener(new SwipeHelper(getBaseContext()){
            @Override
            public void onSwipeLeftToRight(){
                Toast.makeText(getBaseContext(), "swipe right", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSwipeRightToLeft(){
                Toast.makeText(getBaseContext(), "swipe left", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLoadView(int index){
        id.setText(arrayList.get(index).getId());
        name.setText(arrayList.get(index).getName());
        email.setText(arrayList.get(index).getEmail());
        address.setText(arrayList.get(index).getAddress());
        gender.setText(arrayList.get(index).getGender());
    }

//    public ArrayList<Contacts> loadJSONFromAssest(){
//        ArrayList<Contacts> contactList = new ArrayList<>();
//        String json = null;
//        try {
//            InputStream is = getAssets().open("file.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex){
//            ex.printStackTrace();
//            return null;
//        }
//        try {
//            JSONObject obj = new JSONObject(json);
//            JSONArray array = obj.getJSONArray("contacts");
//
//            for(int i=0; i<array.length(); i++){
//                JSONObject jo = array.getJSONObject(i);
//                Contacts contact = new Contacts();
//                contact.setId(jo.getString("id"));
//                contact.setName(jo.getString("name"));
//                contact.setEmail(jo.getString("email"));
//                contact.setAddress(jo.getString("address"));
//                contact.setGender(jo.getString("gender"));
//
//                contactList.add(contact);
//            }
//        } catch (JSONException e){
//            e.printStackTrace();
//        }
//        return contactList;
//    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            // loader
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();

            // creating the request
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray contacts = jsonObject.getJSONArray("contacts");

                    for(int i =0; i<contacts.length();i++){
                        JSONObject jo = contacts.getJSONObject(i);
                        Contacts contact = new Contacts();

                        contact.setId(jo.getString("id"));
                        contact.setName(jo.getString("name"));
                        contact.setEmail(jo.getString("email"));
                        contact.setAddress(jo.getString("address"));
                        contact.setGender(jo.getString("gender"));

                        arrayList.add(contact);
                    }
                } catch ( final JSONException e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            onLoadView(currentIndex);
        }
    }
}
