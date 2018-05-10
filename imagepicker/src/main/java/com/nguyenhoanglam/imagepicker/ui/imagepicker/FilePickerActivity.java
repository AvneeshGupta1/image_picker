package com.nguyenhoanglam.imagepicker.ui.imagepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.adapter.DividerItemDecoration;
import com.nguyenhoanglam.imagepicker.adapter.FileListAdapter;
import com.nguyenhoanglam.imagepicker.listener.FileAdapterListener;
import com.nguyenhoanglam.imagepicker.listener.FileResultCallback;
import com.nguyenhoanglam.imagepicker.model.Document;
import com.nguyenhoanglam.imagepicker.model.FilePickerConst;
import com.nguyenhoanglam.imagepicker.model.PickerManager;
import com.nguyenhoanglam.imagepicker.task.DocScannerTask;
import com.nguyenhoanglam.imagepicker.task.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akumar29 on 9/19/2017.
 */

public class FilePickerActivity extends AppCompatActivity implements FileResultCallback<Document>, FileAdapterListener {

    private RecyclerView recyclerView;
    private TextView emptyView;
    private ProgressBar pb;
    private int type;
    private MenuItem done_menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);
        recyclerView = findViewById(R.id.recyclerview);
        emptyView = findViewById(R.id.empty_view);
        pb = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.line_divider), Utils.dpToPx(70), 0));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.select_audio_file);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initView();
        new DocScannerTask(this, this).execute();
    }

    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            ArrayList<String> selectedPaths = intent.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);
            type = intent.getIntExtra(FilePickerConst.EXTRA_PICKER_TYPE, FilePickerConst.MEDIA_PICKER);
            setToolbarTitle(PickerManager.getInstance().getCurrentCount());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.picker_menu, menu);
        done_menu = menu.findItem(R.id.action_done);
        done_menu.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_done) {
            if (type == FilePickerConst.MEDIA_PICKER)
                returnData(PickerManager.getInstance().getSelectedFiles());
            else
                returnData(PickerManager.getInstance().getSelectedFiles());

            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnData(ArrayList<String> paths) {
        Intent intent = new Intent();
        if (type == FilePickerConst.MEDIA_PICKER)
            intent.putStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA, paths);
        else
            intent.putStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS, paths);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onResultCallback(List<Document> files) {
        pb.setVisibility(View.GONE);
        if (files != null && files.size() > 0) {
            FileListAdapter fileListAdapter = (FileListAdapter) recyclerView.getAdapter();
            if (fileListAdapter == null) {
                fileListAdapter = new FileListAdapter(this, files, PickerManager.getInstance().getSelectedFiles(), this);
                recyclerView.setAdapter(fileListAdapter);
            } else {
                fileListAdapter.setData(files);
                fileListAdapter.notifyDataSetChanged();
            }
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onItemSelected() {
        setToolbarTitle(PickerManager.getInstance().getCurrentCount());
    }

    private void setToolbarTitle(int count) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (done_menu != null)
                done_menu.setVisible(count != 0);
            //if (PickerManager.getInstance().getMaxCount() > 1) {
            actionBar.setTitle(String.format(getString(R.string.selected), count));
//            } else {
//                actionBar.setTitle(R.string.select_doc_text);
//            }
        }

    }
}
