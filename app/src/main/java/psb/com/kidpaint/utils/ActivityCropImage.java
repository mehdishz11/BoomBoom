package psb.com.kidpaint.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.system.ErrnoException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.R;

public class ActivityCropImage extends AppCompatActivity {

    public static final String CROP_SHAPE_RECTANGLE = "RECTANGLE";
    public static final String CROP_SHAPE_OVAL = "OVAL";

    public static final String KEY_HAS_ASPECT = "KEY_HAS_ASPECT";
    public static final String KEY_ASPECT_RADIO_X = "ASPECT_RADIO_X";
    public static final String KEY_ASPECT_RADIO_Y = "ASPECT_RADIO_Y";

    public static final String KEY_CROP_SHAPE = "CROP_SHAPE";

    public static final String KEY_IMAGE_SIZE_X = "IMAGE_SIZE_X";
    public static final String KEY_IMAGE_SIZE_Y = "IMAGE_SIZE_X";


    private CropImageView mCropImageView;
    private Uri mCropImageUri;
    boolean isload = false;

    int aspectRatioX = 1;
    int aspectRatioY = 1;

    String cropShape = CROP_SHAPE_RECTANGLE;

    int imageSizeX = 256;
    int imageSizeY = 256;

    boolean hasAspect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);


        aspectRatioX = getIntent().getIntExtra(KEY_ASPECT_RADIO_X, 1);
        aspectRatioY = getIntent().getIntExtra(KEY_ASPECT_RADIO_Y, 1);

        imageSizeX = getIntent().getIntExtra(KEY_IMAGE_SIZE_X, 256);
        imageSizeY = getIntent().getIntExtra(KEY_IMAGE_SIZE_Y, 256);

        if (getIntent().hasExtra(KEY_CROP_SHAPE)) {
            cropShape = getIntent().getStringExtra(KEY_CROP_SHAPE);
        }

        mCropImageView = findViewById(R.id.image_crop);

        if (getIntent().getBooleanExtra(KEY_HAS_ASPECT, true)) {
            mCropImageView.setAspectRatio(aspectRatioX, aspectRatioY);
            mCropImageView.setFixedAspectRatio(true);
        }


        if (CROP_SHAPE_RECTANGLE.equals(cropShape)) {
            mCropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);
        } else {
            mCropImageView.setCropShape(CropImageView.CropShape.OVAL);
        }

        Button btnCrop = findViewById(R.id.button_do_crop);
        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCropImageClick();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return;
        }
        if (!isload) {
            onLoadImageClick();
        }
    }

    /**
     * On load image button click, start pick  image chooser activity.
     */
    public void onLoadImageClick() {
        isload = true;
        startActivityForResult(getPickImageChooserIntent(), 200);

    }

    /**
     * Crop the image and set it back to the  cropping view.
     */
    public void onCropImageClick() {
        Bitmap cropped = mCropImageView.getCroppedImage(imageSizeX, imageSizeY);
        Uri uri = mCropImageView.getImageUri();
        if (cropped != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            cropped.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Log.d("onCropImageClick", uri + "");
            isload = false;
            Intent intent = new Intent();
            intent.putExtra("image", byteArray);
            intent.putExtra("Uri", uri);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri = getPickImageResultUri(data);
            mCropImageView.setImageUriAsync(imageUri);
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mCropImageView.setImageUriAsync(mCropImageUri);
            onLoadImageClick();
        } else if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            finish();
        }
    }

    public Intent getPickImageChooserIntent() {

// Determine Uri of camera image to  save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

// collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

// collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

// the main intent is the last in the  list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

// Create a chooser from the main  intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

// Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    @SuppressLint("NewApi")
    public boolean isUriRequiresPermissions(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            InputStream stream = resolver.openInputStream(uri);
            stream.close();
            return false;
        } catch (FileNotFoundException e) {
            if (e.getCause() instanceof ErrnoException) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        isload = false;
        super.onBackPressed();
    }
}
