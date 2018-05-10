package xavient.com.imagepickerlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPickMultipleImage;
    private Button btnPickSingleImage;
    private Button btnCaptureImage;
    private static final int REQUEST_IMAGE_MULTIPLE_SELECT = 123;
    private static final int REQUEST_IMAGE_GET = 124;
    private static final int CAPTURE_PHOTO = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPickMultipleImage = findViewById(R.id.btn_pick_multiple_image);
        btnPickMultipleImage.setOnClickListener(this);
        btnPickSingleImage = findViewById(R.id.btn_pick_single_image);
        btnPickSingleImage.setOnClickListener(this);
        btnCaptureImage = findViewById(R.id.btn_capture_image);
        btnCaptureImage.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_MULTIPLE_SELECT && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            Log.d("", "");
        }
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            Log.d("", "");
        }
        if (requestCode == CAPTURE_PHOTO && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            Log.d("", "");
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnPickMultipleImage) {
            ImagePicker.with(this, REQUEST_IMAGE_MULTIPLE_SELECT)               //  Initialize ImagePicker with activity or fragment context
                    .setCameraOnly(false)
                    .setMultipleMode(true)
                    .setShowCamera(false)
                    .setMaxSize(4)
                    .start();
        } else if (view == btnPickSingleImage) {
            ImagePicker.with(this, REQUEST_IMAGE_GET)               //  Initialize ImagePicker with activity or fragment context
                    .setCameraOnly(false)
                    .setMultipleMode(false)
                    .setShowCamera(true)
                    .start();
        } else if (view == btnCaptureImage) {
            ImagePicker.with(this, CAPTURE_PHOTO)               //  Initialize ImagePicker with activity or fragment context
                    .setCameraOnly(true)
                    .setMultipleMode(false)
                    .setShowCamera(true)
                    .start();
        }
    }
}
