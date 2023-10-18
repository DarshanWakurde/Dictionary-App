package com.example.a;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText word;
    ImageView search;
    TextView ans,des,synonyms,partofSpeech;
    Button btn;
    MediaPlayer mediaPlayer;
RequestQueue res;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        word=findViewById(R.id.data);
        search=findViewById(R.id.search);
        ans=findViewById(R.id.word);
        des=findViewById(R.id.worddes);
        btn=findViewById(R.id.pronounce);
        synonyms=findViewById(R.id.synom);
partofSpeech=findViewById(R.id.partSpeech);
res=Volley.newRequestQueue(getApplicationContext());


search.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        searchWord(word.getText().toString());
    }
});








    }

    private void searchWord(String toString) {

        Toast.makeText(this, toString, Toast.LENGTH_SHORT).show();
        String url="https://api.dictionaryapi.dev/api/v2/entries/en/".concat(toString);

        final String[] soundurl = {""};

        JsonArrayRequest arr=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj=response.getJSONObject(i);

                        JSONArray arr=obj.getJSONArray("phonetics");

                        for(int j=0;j<arr.length();j++) {
                            if(!arr.getJSONObject(j).getString("audio").isEmpty()) {
                                soundurl[0] = arr.getJSONObject(j).getString("audio");
                            }
                        }
                        ans.setText(obj.getString("word"));

                        JSONObject obj2=obj.getJSONArray("meanings").getJSONObject(0);
                                        JSONObject obj3=obj2.getJSONArray("definitions").getJSONObject(0);
//                        for(int j=0;j<arr.length();j++){
//                            JSONObject obj2=response.getJSONObject(0);
                        des.setText(obj3.getString("definition"));

                        obj2.getString("partOfSpeech");
                        int length= obj2.getJSONArray("synonyms").length();

                        StringBuilder stringBuilder=new StringBuilder();
                        for(int c=0;c<length;c++){
                            stringBuilder.append(obj2.getJSONArray("synonyms").getString(c)+",");
                        }
                        synonyms.setText(stringBuilder.toString());
                        partofSpeech.setText(obj2.getString("partOfSpeech"));
                        Toast.makeText(MainActivity.this, obj2.getString("partOfSpeech"), Toast.LENGTH_SHORT).show();







                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }


                String audioUrl = soundurl[0];

                // initializing media player
                mediaPlayer = new MediaPlayer();

                // below line is use to set the audio
                // stream type for our media player.
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                // below line is use to set our
                // url to our media player.
                try {
                    mediaPlayer.setDataSource(audioUrl);
                    // below line is use to prepare
                    // and start our media player.
                    mediaPlayer.prepare();
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mediaPlayer.start();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }



            }
        , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        res.add(arr);
}

}