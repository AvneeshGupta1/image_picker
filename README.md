# image_picker
This is customize image picker
 
  you need to add in your strings.xml files  if you want to change colors
  
    <string name="color_toolbar">#FF0000</string>
    <string name="color_status_bar">#00FF00</string>
    <string name="color_toolbar_text">#FFFFFF</string>
    <string name="color_toolbar_icon">#FFFFFF</string>
    <string name="color_progress_bar">#006699</string>
    <string name="color_background">#FFFFFF</string>


add these activity in your menifest

        <activity
            android:name="com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity"
            android:configChanges="orientation|screenSize"
            />
        <activity
            android:name="com.nguyenhoanglam.imagepicker.ui.imagepicker.FilePickerActivity"
            android:configChanges="orientation|screenSize"
             />

        <activity
            android:name="com.nguyenhoanglam.imagepicker.ui.camera.CameraActivty"
            android:screenOrientation="portrait"
            />
            
            gradle file 
            
            implementation 'com.github.AvneeshGupta1:image_picker:-SNAPSHOT'
            
            maven { url "https://jitpack.io" }
            
            
            if you want to pick single image 
            
            ImagePicker.with(this, REQUEST_IMAGE_GET)   
                    .setCameraOnly(false)
                    .setMultipleMode(false)
                    .setShowCamera(true)
                    .start();
                    
                    
            if you want to pick multiple image
            ImagePicker.with(this, REQUEST_IMAGE_MULTIPLE_SELECT)             
                    .setCameraOnly(false)
                    .setMultipleMode(true)
                    .setShowCamera(false)
                    .setMaxSize(4)
                    .start();
                    
           if you want to capture image
           
           ImagePicker.with(this, CAPTURE_PHOTO)               //  Initialize ImagePicker with activity or fragment context
                    .setCameraOnly(true)
                    .setMultipleMode(false)
                    .setShowCamera(true)
                    .start();
                    
                    
          you will get result in 
          
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

                    
