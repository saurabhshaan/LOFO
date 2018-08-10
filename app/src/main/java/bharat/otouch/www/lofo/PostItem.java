package bharat.otouch.www.lofo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class PostItem extends AppCompatActivity{

    private static int  RESULT_LOAD_IMG =0 ;
    EditText ed1,ed2,ed3;
    Button bt;
    String name,num,email;
    String imgDecodableString;
    String encodedImage;
    ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        ed3 = (EditText) findViewById(R.id.ed3);
        bt = (Button) findViewById(R.id.bt);
        imgView = (ImageView) findViewById(R.id.imageview);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ed1.getText().toString().trim();
                num = ed2.getText().toString().trim();
                email = ed3.getText().toString().trim();

                Toast.makeText(PostItem.this, "Name: " + name + " Num: " + num + " Add: " + email, Toast.LENGTH_SHORT).show();
                String method = "register";

                if (isOnline()) {

                    Toast.makeText(PostItem.this, "Connection is ok", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "bt start");
                    BackgroundTask backgroundTask = new BackgroundTask(PostItem.this);
                    backgroundTask.execute(method, name, num, email,encodedImage);
                    Log.d("TAG", "bt end");

                } else {
                    Toast.makeText(PostItem.this, "Connection is Offline", Toast.LENGTH_SHORT).show();
                }


            }//registrationEnd


            public boolean isOnline() {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cm.getActiveNetworkInfo();
                return info != null && info.isConnectedOrConnecting();
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

    public void browseImage(View view) {

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
                        // Toast.makeText(this, "Picture saved at " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                        Toast.makeText(this, "ImageSet", Toast.LENGTH_SHORT).show();
                        imgView.setImageBitmap(thumbnail);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        if (thumbnail != null) {
                            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object//0 for low quality
                        }
                        byte[] b = baos.toByteArray();
                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        Toast.makeText(this,encodedImage , Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Wait for moment ....", Toast.LENGTH_SHORT).show();
                        break;

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
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
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
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

                //imageUploadSTART

                Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object//0 for low quality
                byte[] b = baos.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                Log.d("error", "images" + encodedImage);
                Toast.makeText(PostItem.this, "Wait for moment ....", Toast.LENGTH_SHORT).show();
                Log.d("error", "images" + encodedImage);
                Toast.makeText(PostItem.this, "ImageSet", Toast.LENGTH_SHORT).show();
                //close
            }
        } catch (Exception e) {
            Toast.makeText(this, "Problem Detected!"+e, Toast.LENGTH_LONG).show();
            Log.d("error","lo"+e);
        }
    }
}