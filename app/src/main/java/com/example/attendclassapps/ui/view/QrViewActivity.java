package com.example.attendclassapps.ui.view;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.attendclassapps.ui.generator.Qr_GeneratorAndReview;
import com.example.attendclassapps.ui.generator.Qr_GetTextForGenerate;
import com.example.attendclassapps.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QrViewActivity extends AppCompatActivity {

    private LinearLayout clearBt;
    private AppCompatImageView qrCodeView;
    private Bitmap qrBitmap;
    private AppCompatTextView textViewShow;
    SharedPreferences sharedPreferences;
    Qr_GetTextForGenerate qr_getTextForGenerate;
    Qr_GeneratorAndReview qr_generatorAndReview;
    private static final String TAG = "Log_Attend_Class";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_view);

        setTitle(getString(R.string.label_qr_code_view));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindView();

        qr_getTextForGenerate = new Qr_GetTextForGenerate(QrViewActivity.this);
        qr_generatorAndReview = new Qr_GeneratorAndReview(QrViewActivity.this);
        sharedPreferences = this.getSharedPreferences(Qr_GetTextForGenerate.subjectSharedPreference , MODE_PRIVATE);
        String subjectName = sharedPreferences.getString(Qr_GetTextForGenerate.subject_name_KEY , null);
        String subjectID = sharedPreferences.getString(Qr_GetTextForGenerate.subject_id_KEY , null);

        if (subjectName == null && subjectID == null){
            clearBt.setVisibility(View.GONE);
            textViewShow.setText(R.string.ask);
            Log.i(TAG, "QrViewActivity_onCreate_Show Text");
        }
        else {
            /*MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            String SubjectName_create = qr_generatorAndReview.getSubjectName_toCreate();
            String SubjectID_create = qr_generatorAndReview.getSubjectID_toCreate();
            try{
                BitMatrix bitMatrix = multiFormatWriter.encode((SubjectName_create +"\n"+ SubjectID_create), BarcodeFormat.QR_CODE, 200, 200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                qr_generatorAndReview.getBitmap(bitmap);
            }catch (WriterException e){
                e.printStackTrace();
            }*/
            clearBt.setVisibility(View.VISIBLE);
            qrBitmap = qr_generatorAndReview.showBitmap();
            qrCodeView.setImageBitmap(qrBitmap);
            Log.i(TAG, "QrViewActivity_onCreate_Get Bitmap and Review");
        }

        clearBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(Qr_GetTextForGenerate.subjectSharedPreference , MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit().clear();
                editor.commit();
                qrCodeView.setVisibility(View.GONE);
                qr_generatorAndReview.destroyBitmap(qrBitmap);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Student_join").child("Class_room");
                databaseReference.removeValue();
                Toast.makeText(QrViewActivity.this, "Class Attend Checked", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "QrViewActivity_onCreate_Bitmap has been Destroyed and Firebase Data has been deleted");
            }
        });
        Log.i(TAG, "QrViewActivity_onCreate");
    }

    protected void bindView(){
        clearBt = this.findViewById(R.id.vBtnClearSharedPrefBt);
        qrCodeView = this.findViewById(R.id.ivQRCode);
        textViewShow = this.findViewById(R.id.textShow);
    }

}