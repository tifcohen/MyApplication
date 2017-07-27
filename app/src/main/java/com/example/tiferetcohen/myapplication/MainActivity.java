package com.example.tiferetcohen.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ArrayList<Contacts> arrayList;
    TextView id;
    TextView name;
    TextView email;
    TextView address;
    TextView gender;
    RelativeLayout relativeLayout;
    Button nextBtn;
    int currentIndex;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = loadJSONFromAssest();

        relativeLayout = (RelativeLayout) findViewById(R.id.container);
        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        address = (TextView) findViewById(R.id.address);
        gender = (TextView) findViewById(R.id.gender);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        currentIndex = 0;

        onLoadView(currentIndex);
        gestureDetector = new GestureDetector(MainActivity.this, MainActivity.this);

        final Intent intent = new Intent(this, Main2Activity.class);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
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

    public ArrayList<Contacts> loadJSONFromAssest(){
        ArrayList<Contacts> contactList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open("file.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("contacts");

            for(int i=0; i<array.length(); i++){
                JSONObject jo = array.getJSONObject(i);
                Contacts contact = new Contacts();
                contact.setId(jo.getString("id"));
                contact.setName(jo.getString("name"));
                contact.setEmail(jo.getString("email"));
                contact.setAddress(jo.getString("address"));
                contact.setGender(jo.getString("gender"));

                contactList.add(contact);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return contactList;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX()-e2.getX() > 50){ // swipe left
            if(currentIndex == arrayList.size()-1) {
                Toast.makeText(MainActivity.this, "End of list", Toast.LENGTH_LONG).show();
            } else if(currentIndex >= 0){
                currentIndex +=1;
                onLoadView(currentIndex);
            }
            return true;
        }
        if(e2.getX()-e1.getX() >50){ // swipe right
            if(currentIndex == 0) {
                Toast.makeText(MainActivity.this, "Beginning of List", Toast.LENGTH_LONG).show();
            }else if(currentIndex <= arrayList.size()){
                currentIndex -=1;
                onLoadView(currentIndex);
            }
            return true;
        }
        else
            return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        return gestureDetector.onTouchEvent(motionEvent);
    }
}
