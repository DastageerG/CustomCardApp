package com.example.customcardapp.viewModel;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

import static com.example.customcardapp.Constants.TAG_OUTPUT;

public class CustomCardViewModel extends AndroidViewModel
{
    private Uri mImageUri;
    private WorkManager mWorkManager;
    private LiveData<List<WorkInfo>>mSaveWorkInfo;
    private Uri outPutUri;

    public CustomCardViewModel(@NonNull Application application)
    {
        super(application);
        mWorkManager = WorkManager.getInstance(application);

        mSaveWorkInfo = mWorkManager.getWorkInfosByTagLiveData(TAG_OUTPUT);

    } // CustomCardViewModel closed



    LiveData<List<WorkInfo>>getOutPutWorkInfo()
    {
        return mSaveWorkInfo;
    } /// getOutPutWorkInfo closed

    public void setOutPutUri(String imageUri)
    {
        outPutUri = uriORNull(imageUri);
    } // seOutPutUri closed

    private Uri uriORNull(String uriString)
    {
        if(!TextUtils.isEmpty(uriString))
        {
            return Uri.parse(uriString);
        }
        return null;
    }

    public Uri getImageUri()
    {
        return mImageUri;
    }

    public void setImageUri(String imageUri)
    {
        mImageUri = uriORNull(imageUri);
    }


    public void processImageToCard(String quote)
    {

    }


} // CustomCardViewModel
