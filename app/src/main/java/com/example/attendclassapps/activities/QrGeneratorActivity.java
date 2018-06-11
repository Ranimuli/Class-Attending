package com.example.attendclassapps.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.attendclassapps.Qr_GetTextForGenerate;
import com.example.attendclassapps.R;

public class QrGeneratorActivity extends AppCompatActivity {

    private AppCompatEditText SubjectNameText, SubjectIdText;
    private String setTextSjName, setTextSjId;
    private LinearLayout generateBt,qrViewBt;
    public SharedPreferences sharedPreferences;
    Qr_GetTextForGenerate qr_getTextForGenerate;
    private static final String TAG = "Log_Attend_Class";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);
        setTitle("QR Code Generator");

        setTitle(getString(R.string.label_qr_code_generator));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindView();

        generateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextSjName = SubjectNameText.getText().toString().trim();
                setTextSjId = SubjectIdText.getText().toString().trim();
                qr_getTextForGenerate = new Qr_GetTextForGenerate(QrGeneratorActivity.this);
                qr_getTextForGenerate.setSubjectName(setTextSjName);
                qr_getTextForGenerate.setSubjectId(setTextSjId);
                startActivity(new Intent(getApplicationContext(),QrViewActivity.class));
            }
        });

        qrViewBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QrViewActivity.class));
            }
        });
        /*sharedPreferences = getSharedPreferences(sp_KEY, Context.MODE_PRIVATE);
        SubjectNameText.setText(sharedPreferences.getString(subjectName_KEY, ""));
        SubjectIdText.setText(sharedPreferences.getString(subjectId_KEY,""));*/
        SubjectNameText.setText("");
        SubjectIdText.setText("");
        Log.i(TAG, "QrGenActivity_onCreate");
    }

    /*@Override
    protected void onDestroy(){
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(subjectName_KEY , SubjectNameText.getText().toString());
        editor.putString(subjectId_KEY , SubjectIdText.getText().toString());
        editor.commit();
        Log.i(TAG, "QrGenActivity_onDestroy");
    }*/

    /*@Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "QrGenActivity_onPause");
    }*/

    protected void bindView(){
        qrViewBt = this.findViewById(R.id.vBtnQRView);
        generateBt = this.findViewById(R.id.vBtnQRGen);
        SubjectNameText = this.findViewById(R.id.inSJName);
        SubjectIdText = this.findViewById(R.id.inSJid);
    }
}