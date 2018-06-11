package com.example.attendclassapps;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Qr_GeneratorAndReview {

    Bitmap bitmap;
    Context context;
    private SharedPreferences sharedPreferences;
    String subjectName, subjectID;
    public static final  String subjectSharedPreference = "subject" , subject_name_KEY = "subjectName" , subject_id_KEY = "subjectID";

    public Qr_GeneratorAndReview(){

    }
    public Qr_GeneratorAndReview(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(subjectSharedPreference, Context.MODE_PRIVATE);
        createQrCode();
    }

    /*public Qr_GeneratorAndReview(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }*/

    public void createQrCode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        String SubjectName_create = getSubjectName_toCreate();
        String SubjectID_create = getSubjectID_toCreate();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode((SubjectName_create +" "+ SubjectID_create), BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            getBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }

    public String getSubjectName_toCreate() {
        subjectName = sharedPreferences.getString(subject_name_KEY, "");
        return subjectName;
    }
    public String getSubjectID_toCreate() {
        subjectID = sharedPreferences.getString(subject_id_KEY, "");
        return subjectID;
    }

    public void getBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap showBitmap() {
        return bitmap;
    }

    public void destroyBitmap(Bitmap bitmap) {
        bitmap.recycle();
    }
}
