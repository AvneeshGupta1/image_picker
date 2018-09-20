# image_picker
This is customize image picker

override the strings 


<string name="color_toolbar">#FF0000</string>
    <string name="color_status_bar">#00FF00</string>
    <string name="color_toolbar_text">#FFFFFF</string>
    <string name="color_toolbar_icon">#FFFFFF</string>
    <string name="color_progress_bar">#006699</string>
    <string name="color_background">#FFFFFF</string>

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
