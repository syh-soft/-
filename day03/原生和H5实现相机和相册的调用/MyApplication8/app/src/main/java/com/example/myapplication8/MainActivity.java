package com.example.myapplication8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    //private ImageView picture;
    private Uri ImageUri;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new JsBridge(),"open");
        webView.loadUrl("file:///android_asset/调用.html");
        /*
          Button take_photo = findViewById(R.id.take_photo);
          Button choose_picture = findViewById(R.id.choose_picture);
          picture = findViewById(R.id.picture);
        choose_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                }else{
                    openAlbum();
                }
            }
        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outimage = new File(getExternalCacheDir(),"output_image.jpg");
                try {
                    if(outimage.exists()){
                        outimage.delete();
                    }
                    outimage.createNewFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    ImageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.myapplication8.fileprovider",outimage);

                }else{
                    ImageUri = Uri.fromFile(outimage);
                }
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,ImageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });

         */
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType( "image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }
                else{
                    Toast.makeText(MainActivity.this,"用户拒绝了你的请求",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                         //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(ImageUri));
                         System.out.println(123);
                         //picture.setImageBitmap(bitmap);
                        String imagepath = getExternalCacheDir().toString() +"/"+ "output_image.jpg";
                        String js = "javascript:" + "change('" + imagepath + "')";
                        webView.evaluateJavascript(js, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                            }

                        });
                        System.out.println(456);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data);
                    } else {
                        handleImageBeforeKitkat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitkat(Intent data) {
        String imagepath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                System.out.println(id);
                String selection = MediaStore.Images.Media._ID+ "="+id;
                imagepath = getimagepath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagepath = getimagepath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagepath = getimagepath(uri,null);

        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagepath = uri.getPath();
        }
        displayImage(imagepath);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageBeforeKitkat(Intent data) {
        Uri uri = data.getData();
        String imagepath = getimagepath(uri,null);
        displayImage(imagepath);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void displayImage(String imagepath) {
        if(imagepath != null){
            System.out.println(imagepath);
            //Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            //picture.setImageBitmap(bitmap);
            String js = "javascript:" + "change('" + imagepath + "')";
            webView.evaluateJavascript(js, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    System.out.println(value);
                    System.out.println(456);
                }

            });
        }else{
            Toast.makeText(this,"选择图像失败",Toast.LENGTH_SHORT).show();
        }
    }

    private String getimagepath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToNext()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    private class JsBridge{
        @JavascriptInterface
        public void showCamera(){
            File outimage = new File(getExternalCacheDir(),"output_image.jpg");
            try {
                if(outimage.exists()){
                    outimage.delete();
                }
                outimage.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
            if(Build.VERSION.SDK_INT>=24){
                ImageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.myapplication8.fileprovider",outimage);

            }else{
                ImageUri = Uri.fromFile(outimage);
            }
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,ImageUri);
            startActivityForResult(intent,TAKE_PHOTO);
        }

        @JavascriptInterface
        public void showGallary(){
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

            }else{
                openAlbum();
            }
        }

    }



}