package com.example.tuantx.socketclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private ListView mListviewUser;
    private ListView mListviewChat;
    EditText mEdtContent;
    ImageView mImageAdd, mImageSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mSocket = IO.socket("http://192.168.1.36:3000");

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mSocket.connect();
        mSocket.on("server-send-data", onRetreiveData);
        mSocket.emit("client-send-data","Trinh Xuan Tuan");

    }


    private Emitter.Listener onRetreiveData = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        String data = jsonObject.getString("noidung");
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


}
