/*
 * Created by Nguyen Hoang Lam
 * Date: ${DATE}
 */

package com.nguyenhoanglam.imagepicker.ui.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.model.SavePath;
import com.nguyenhoanglam.imagepicker.ui.camera.CameraActivty;

import java.util.ArrayList;

/**
 * Created by hoanglam on 8/4/16.
 */
public class ImagePicker {

    protected Config config;


    public ImagePicker(Builder builder) {
        config = builder.config;
    }

    public static Builder with(Activity activity, int requestCode) {
        ActivityBuilder builder = new ActivityBuilder(activity, Config.TYPE_IMAGE, requestCode);
        builder.setFolderTitle(activity.getString(R.string.title_folder))
                .setImageTitle(activity.getString(R.string.title_image))         //  Picture title (works with FolderMode = false)
                .setDoneTitle(activity.getString(R.string.title_done))               //  Done button title
                .setToolbarColor(activity.getString(R.string.color_toolbar))         //  Toolbar color
                .setStatusBarColor(activity.getString(R.string.color_status_bar))       //  StatusBar color (works with SDK >= 21  )
                .setToolbarTextColor(activity.getString(R.string.color_toolbar_text))     //  Toolbar text color (Title and Done button)
                .setToolbarIconColor(activity.getString(R.string.color_toolbar_icon))     //  Toolbar icon color (Back and Camera button)
                .setProgressBarColor(activity.getString(R.string.color_progress_bar))     //  ProgressBar color
                .setBackgroundColor(activity.getString(R.string.color_background))  //  Select multiple images or single image
                .setFolderMode(true);              //  Folder mode
        return builder;
    }

//    public static Builder with(Fragment fragment) {
//        return new FragmentBuilder(fragment);
//    }

    static class ActivityBuilder extends Builder {
        private Activity activity;
        private int resourceType;
        private int requestCode;

        public ActivityBuilder(Activity activity, int resourceType, int requestCode) {
            super(activity);
            this.activity = activity;
            this.resourceType = resourceType;
            this.requestCode = requestCode;
        }

        @Override
        public void start() {
            Intent intent;
            if (!config.isCameraOnly() && (resourceType == Config.TYPE_IMAGE)) {
                intent = new Intent(activity, ImagePickerActivity.class);
                intent.putExtra(Config.EXTRA_CONFIG, config);
                intent.putExtra(Config.EXTRA_REQUEST_CODE, requestCode);
                intent.putExtra(Config.EXTRA_RESOURCE_TYPE, resourceType);
                activity.startActivityForResult(intent, requestCode);
                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
            } else if (resourceType == Config.TYPE_IMAGE) {
                intent = new Intent(activity, CameraActivty.class);
                intent.putExtra(Config.EXTRA_REQUEST_CODE, requestCode);
                intent.putExtra(Config.EXTRA_CONFIG, config);
                intent.putExtra(Config.EXTRA_RESOURCE_TYPE, resourceType);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(Config.EXTRA_REQUEST_CODE, requestCode);
                activity.overridePendingTransition(0, 0);
                activity.startActivityForResult(intent, requestCode);
            }
        }
    }

    public static abstract class Builder extends BaseBuilder {


        public Builder(Activity activity) {
            super(activity);
        }

        public Builder(Fragment fragment) {
            super(fragment.getContext());
        }

        public Builder setToolbarColor(String toolbarColor) {
            config.setToolbarColor(toolbarColor);
            return this;
        }

        public Builder setStatusBarColor(String statusBarColor) {
            config.setStatusBarColor(statusBarColor);
            return this;
        }

        public Builder setToolbarTextColor(String toolbarTextColor) {
            config.setToolbarTextColor(toolbarTextColor);
            return this;
        }

        public Builder setToolbarIconColor(String toolbarIconColor) {
            config.setToolbarIconColor(toolbarIconColor);
            return this;
        }

        public Builder setProgressBarColor(String progressBarColor) {
            config.setProgressBarColor(progressBarColor);
            return this;
        }

        public Builder setBackgroundColor(String backgroundColor) {
            config.setBackgroundColor(backgroundColor);
            return this;
        }

        public Builder setCameraOnly(boolean isCameraOnly) {
            config.setCameraOnly(isCameraOnly);
            return this;
        }

        public Builder setMultipleMode(boolean isMultipleMode) {
            config.setMultipleMode(isMultipleMode);
            return this;
        }

        public Builder setFolderMode(boolean isFolderMode) {
            config.setFolderMode(isFolderMode);
            return this;
        }

        public Builder setShowCamera(boolean isShowCamera) {
            config.setShowCamera(isShowCamera);
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            config.setMaxSize(maxSize);
            return this;
        }

        public Builder setDoneTitle(String doneTitle) {
            config.setDoneTitle(doneTitle);
            return this;
        }

        public Builder setFolderTitle(String folderTitle) {
            config.setFolderTitle(folderTitle);
            return this;
        }

        public Builder setImageTitle(String imageTitle) {
            config.setImageTitle(imageTitle);
            return this;
        }

        public Builder setSavePath(String path) {
            config.setSavePath(new SavePath(path, false));
            return this;
        }

        public Builder setSelectedImages(ArrayList<Image> selectedImages) {
            config.setSelectedImages(selectedImages);
            return this;
        }

        public abstract void start();

    }

    public static abstract class BaseBuilder {

        protected Config config;

        public BaseBuilder(Context context) {
            this.config = new Config();

            Resources resources = context.getResources();
            config.setCameraOnly(false);
            config.setMultipleMode(true);
            config.setFolderMode(true);
            config.setShowCamera(true);
            config.setMaxSize(Config.MAX_SIZE);
            config.setDoneTitle(resources.getString(R.string.imagepicker_action_done));
            config.setFolderTitle(resources.getString(R.string.imagepicker_title_folder));
            config.setImageTitle(resources.getString(R.string.imagepicker_title_image));
            config.setSavePath(SavePath.DEFAULT);
            config.setSelectedImages(new ArrayList<Image>());
        }
    }

}
