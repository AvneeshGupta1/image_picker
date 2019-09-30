/*
 * Created by Nguyen Hoang Lam
 * Date: ${DATE}
 */

package com.nguyenhoanglam.imagepicker.ui.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;

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
        int colorToolbar = activity.getResources().getColor(R.color.color_toolbar);
        int colorStatusBar = activity.getResources().getColor(R.color.color_status_bar);
        int colorToolbarText = activity.getResources().getColor(R.color.color_toolbar_text);
        int colorToolbarIconColor = activity.getResources().getColor(R.color.color_toolbar_icon);
        int colorToolbarProgress = activity.getResources().getColor(R.color.color_progress_bar);
        int colorBackground = activity.getResources().getColor(R.color.color_background);

        ActivityBuilder builder = new ActivityBuilder(activity, Config.TYPE_IMAGE, requestCode);
        builder.setFolderTitle(activity.getString(R.string.title_folder))
                .setImageTitle(activity.getString(R.string.title_image))         //  Picture title (works with FolderMode = false)
                .setDoneTitle(activity.getString(R.string.title_done))
                .setToolbarColor(getIntToColor(colorToolbar))         //  Toolbar color
                .setStatusBarColor(getIntToColor(colorStatusBar))      //  StatusBar color (works with SDK >= 21  )
                .setToolbarTextColor(getIntToColor(colorToolbarText))     //  Toolbar text color (Title and Done button)
                .setToolbarIconColor(getIntToColor(colorToolbarIconColor))     //  Toolbar icon color (Back and Camera button)
                .setProgressBarColor(getIntToColor(colorToolbarProgress))     //  ProgressBar color
                .setBackgroundColor(getIntToColor(colorBackground))  //  Select multiple images or single image
                .setFolderMode(true);              //  Folder mode
        return builder;
    }
    public static Builder with(Fragment fragment,int requestCode) {
        return new FragmentBuilder(fragment,requestCode);
    }
    static class FragmentBuilder extends Builder {
        private Fragment fragment;
        private int requestCode;

        public FragmentBuilder(Fragment fragment, int requestCode) {
            super(fragment);
            this.fragment = fragment;
            this.requestCode = requestCode;
        }


        @Override
        public void start() {
            Intent intent;
            if (!config.isCameraOnly()) {
                intent = new Intent(fragment.getActivity(), ImagePickerActivity.class);
                intent.putExtra(Config.EXTRA_CONFIG, config);
                intent.putExtra(Config.EXTRA_REQUEST_CODE, requestCode);
                intent.putExtra(Config.EXTRA_RESOURCE_TYPE, Config.TYPE_IMAGE);
                fragment.startActivityForResult(intent, requestCode);
                fragment.getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            } else  {
                intent = new Intent(fragment.getActivity(), CameraActivty.class);
                intent.putExtra(Config.EXTRA_REQUEST_CODE, requestCode);
                intent.putExtra(Config.EXTRA_CONFIG, config);
                intent.putExtra(Config.EXTRA_RESOURCE_TYPE, Config.TYPE_IMAGE);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(Config.EXTRA_REQUEST_CODE, requestCode);
                fragment.getActivity().overridePendingTransition(0, 0);
                fragment.startActivityForResult(intent, requestCode);
            }
        }
    }

    public static String getIntToColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
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

