package com.example.a;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.List;

public class Translator extends AppCompatActivity {


    ImageView mic;
    TextInputEditText source;
    Spinner from, to;
    TextView answer;
    Button translate;

    private static final int SPEECH_REQUEST_CODE = 0;
    String [] fromLanguage={"From","English","Afrikaans","Arabics","Belarus","Bulgarian","Bengali","Catalan","Marathi","Gujrati","Czech","Welsh","Hindi","Urdu"};
    String [] toLanguage={"From","English","Afrikaans","Arabics","Belarus","Bulgarian","Bengali","Catalan","Marathi","Gujrati","Czech","Welsh","Hindi","Urdu"};

    private  static  final  int REQUEST_PERMISSION_CODE=1;
    int languageCode,fromLanguageCode,toLanguageCode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tranlator);

        mic=findViewById(R.id.saySomthing);
        source=findViewById(R.id.inputtext);
        from=findViewById(R.id.fromLang);
        to=findViewById(R.id.toLang);
        answer=findViewById(R.id.answer);
        translate=findViewById(R.id.translate);
        from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromLanguageCode=getLanguagecode(fromLanguage[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter fromAdpt=new ArrayAdapter(this,R.layout.spinner,fromLanguage);
        fromAdpt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        from.setAdapter(fromAdpt);

        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toLanguageCode=getLanguagecode(toLanguage[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter toAdpt=new ArrayAdapter(this,R.layout.spinner,fromLanguage);
        toAdpt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        to.setAdapter(toAdpt);



        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setText("");
                if(source.getText().toString().isEmpty()){
                    Toast.makeText(Translator.this, "Please ENter your text to translate", Toast.LENGTH_SHORT).show();
                } else if (fromLanguageCode==0) {
                    Toast.makeText(Translator.this, "Source language", Toast.LENGTH_SHORT).show();
                } else if (toLanguageCode==0) {
                    Toast.makeText(Translator.this, "TO language code", Toast.LENGTH_SHORT).show();
                }
                else{
                    translateText(fromLanguageCode,toLanguageCode,source.getText().toString());
                }
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// This starts the activity and populates the intent with the speech text.
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText.
            source.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void translateText(int fromLanguageCode, int toLanguageCode, String lang) {

        translate.setText("Translating....");
        FirebaseTranslatorOptions options=new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(fromLanguageCode).setTargetLanguage(toLanguageCode).build();

        FirebaseTranslator translator= FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions= new FirebaseModelDownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translate.setText("Translate");
                answer.setText("Translationg buddy.....");
                translator.translate(source.getText().toString()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                            answer.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Translator.this, "SOrry", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
//    String [] toLanguage={"From","English","Afrikaans","Arabics","Belarus","Bulgarian","Bengali","Catalan","Czech","Welsh","Hindi","Urdu"};

    private int getLanguagecode(String s) {
int languageCode=0;
switch (s){
    case "English":
        languageCode= FirebaseTranslateLanguage.EN;
        break;
    case "Afrikaans":
        languageCode= FirebaseTranslateLanguage.AF;
        break;
    case "Arabics":
        languageCode= FirebaseTranslateLanguage.AR;
        break;
    case "Belarus":
        languageCode= FirebaseTranslateLanguage.BE;
        break;
    case "Bulgarian":
        languageCode= FirebaseTranslateLanguage.BG;
        break;
    case "Bengali":
        languageCode= FirebaseTranslateLanguage.BN;
        break;
    case "Catalan":
        languageCode= FirebaseTranslateLanguage.CA;
        break;
    case "Czech":
        languageCode= FirebaseTranslateLanguage.CS;
        break;
    case "Welsh":
        languageCode= FirebaseTranslateLanguage.CY;
        break;
    case "Hindi":
        languageCode= FirebaseTranslateLanguage.HI;
        break;
    case "Urdu":
        languageCode= FirebaseTranslateLanguage.UR;
        break;
    case "Marathi":
        languageCode= FirebaseTranslateLanguage.MR;
        break;
    case "Gujrati":
        languageCode= FirebaseTranslateLanguage.GU;
        break;
    default:
        languageCode=0;



}
        return languageCode;
    }
}