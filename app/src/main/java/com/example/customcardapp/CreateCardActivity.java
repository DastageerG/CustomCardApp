package com.example.customcardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.customcardapp.viewModel.CustomCardViewModel;
import com.squareup.picasso.Picasso;

public class CreateCardActivity extends AppCompatActivity
{

    private static final String TAG = "1234";
    private ImageView imageView;
    private EditText editTextTextOnImage;
    private Button buttonProcessCard;

    private ProgressDialog progressDialog;

    private CustomCardViewModel customCardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        imageView  = findViewById(R.id.imageView);
        editTextTextOnImage = findViewById(R.id.editTextTextOnImage);
        buttonProcessCard = findViewById(R.id.buttonProcessCard);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing Card");
        progressDialog.setMessage("Please wait ... ");
        progressDialog.show();


        customCardViewModel = new ViewModelProvider(CreateCardActivity.this).get(CustomCardViewModel.class);


        if(getIntent().getStringExtra(Constants.KEY_IMAGE_URI)!=null)
        {
            customCardViewModel.setImageUri(getIntent().getStringExtra(Constants.KEY_IMAGE_URI));
        } // if closed

        if(customCardViewModel.getImageUri()!=null)
        {
            progressDialog.dismiss();
            Picasso.get().load(customCardViewModel.getImageUri()).placeholder(R.drawable.ic_launcher_foreground).into(imageView);
        }


    } // onCreate closed

} // CreateCardActivity closed