package com.example.customcardapp;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.customcardapp.Constants.KEY_IMAGE_URI;

public class SelectImageActivity extends AppCompatActivity
{

    public static final String permissionsCount = "permissionsCount";
    private static final int PERMISSION_REQUEST_CODE = 123;
    private static final int PICK_IMAGE_REQUEST = 111;
    private static final String TAG = "1111";
    public static int mPermissionsRequestedCount  = 0;
    public static int permissionRequestMaxCount = 2;
    public static final List<String>sPermissions = Arrays.asList(
            Manifest.permission.READ_EXTERNAL_STORAGE ,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    );
    private Button buttonSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        buttonSelect = findViewById(R.id.buttonSelectImage);
        if(savedInstanceState!=null)
        {
            mPermissionsRequestedCount  = savedInstanceState.getInt(permissionsCount,0);
        }
        requestPermissionsIfNecessary();

        buttonSelect.setEnabled(true);

    } // onCreate closed

    private void requestPermissionsIfNecessary()
    {
        if(! checkCallingPermission())
        {
            if(mPermissionsRequestedCount < permissionRequestMaxCount)
            {
                mPermissionsRequestedCount++;
                ActivityCompat.requestPermissions(SelectImageActivity.this,
                        sPermissions.toArray(new String[0])
                        ,PERMISSION_REQUEST_CODE);
            } // if closed
            else
            {
                Toast.makeText(this, R.string.go_set_permissions, Toast.LENGTH_SHORT).show();
                buttonSelect.setEnabled(false); // buttonDisabled
            }
        } // if closed
        else
        {
            buttonSelect.setEnabled(true);
        }

    } // requestPermissionsIfNecessary

    private boolean checkCallingPermission()
    {
        boolean hasPermissions = true;
        for(String permssions:sPermissions)
        {
            hasPermissions &=  ContextCompat.checkSelfPermission(SelectImageActivity.this,permssions)

            == PackageManager.PERMISSION_GRANTED;
        }
        return hasPermissions;
    }

    public void  onClickSelectImage(View view)
    {
       requestPermissionsIfNecessary();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    } // onClickSelectImage closed

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                Uri uri = null;
                if(data.getClipData()!=null)
                {
                    uri = data.getClipData().getItemAt(0).getUri();
                } // if closed
                else if (data.getData()!=null)
                {
                    uri = data.getData();
                } // else closed

                if(uri==null)
                {
                    Log.e(TAG, "Invalid image input ");
                    return;
                }

                Intent intent = new Intent(SelectImageActivity.this,CreateCardActivity.class);
                intent.putExtra(KEY_IMAGE_URI,uri.toString());
                startActivity(intent);

            } // if closed
            else
            {
                Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
            }

        } // if closed
    } // onActivityResult closed
} // MainActivity closed