package bharat.otouch.www.lofo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Proof extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 0;
    ImageView imgView;
    String imgDecodableString;
    String encodedImage;
    Button bt;
    String s2;
    Bitmap photo;
    SharedPreferences pref;

    EditText ed;
    private Bitmap bmp;
    private String temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof2);

        imgView = (ImageView) findViewById(R.id.imageview);

        bt=(Button)findViewById(R.id.bt);
        ed=(EditText)findViewById(R.id.desc);
        pref=getApplicationContext().getSharedPreferences("MyPref",0);
        s2=pref.getString("key_name",null);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);

                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{s2});
                i.putExtra(Intent.EXTRA_SUBJECT, "Claiming For The Product");
                i.putExtra(Intent.EXTRA_TEXT, "\nDescription : "+ed.getText());
                i.putExtra(Intent.EXTRA_TEXT,photo);

                startActivity(i);

                try {
                    startActivity(Intent.createChooser(i, "Sending Proof..."));
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    public void takePicture(View View) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//MediaStore is type of dtabse whwere image and video storeed.
      /*  imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Test.jpg");//directory path and file name two argument in file
        Toast.makeText(LabourRegistration.this, "Picture Clicked" + imageFile, Toast.LENGTH_SHORT).show();

        //generate path Uri
        Uri value = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, value);//Extraoutput show path for saving file
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//define image quality 0 for low and 1 for high quality
        */
        startActivityForResult(intent, 0);
    }

    public void browseImage(View v) {

// Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra("crop", "true");
        galleryIntent.putExtra("outputX", 200);
        galleryIntent.putExtra("outputY", 260);
        galleryIntent.putExtra("aspectX", 1);
        galleryIntent.putExtra("aspectY", 1);
        galleryIntent.putExtra("scale", true);
        galleryIntent.putExtra("return-data", true);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 0) {
                switch (resultCode) {
                    case Activity.RESULT_OK:

                    photo = (Bitmap) data.getExtras().get("data");
                        imgView.setImageBitmap(photo);

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(this, "Activity.RESULT_CANCELLED", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;


                }

            }//onActivityCamera-END
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                if (cursor != null) {
                    cursor.moveToFirst();
                }

                int columnIndex = 0;
                if (cursor != null) {
                    columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                }
                if (cursor != null) {
                    imgDecodableString = cursor.getString(columnIndex);
                }
                if (cursor != null) {
                    cursor.close();
                }
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                //imageUploadSTART

                Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object//0 for low quality
                byte[] b = baos.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                Toast.makeText(this, "ImageSet", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Wait for moment ....", Toast.LENGTH_SHORT).show();
                Log.d("error", "images" + encodedImage);
                //close
            }
        } catch (Exception e) {
            Toast.makeText(this, "Problem Detected!"+e, Toast.LENGTH_LONG).show();
        }

    }

}
