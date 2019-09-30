package com.nguyenhoanglam.imagepicker.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.listener.FileAdapterListener;
import com.nguyenhoanglam.imagepicker.model.Document;
import com.nguyenhoanglam.imagepicker.model.FilePickerConst;
import com.nguyenhoanglam.imagepicker.model.PickerManager;
import com.nguyenhoanglam.imagepicker.util.SmoothCheckBox;

import java.util.List;


/**
 * Created by droidNinja on 29/07/16.
 */
public class FileListAdapter extends SelectableAdapter<FileListAdapter.FileViewHolder, Document> {


    private final Context context;
    private final FileAdapterListener mListener;

    public FileListAdapter(Context context, List<Document> items, List<String> selectedPaths, FileAdapterListener fileAdapterListener) {
        super(items, selectedPaths);
        this.context = context;
        this.mListener = fileAdapterListener;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_doc_layout, parent, false);
        return new FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FileViewHolder holder, int position) {
        final Document document = getItems().get(position);

        holder.imageView.setImageResource(document.getFileType().getDrawable());
        holder.fileNameTextView.setText(document.getTitle());
        holder.fileSizeTextView.setText(Formatter.formatShortFileSize(context, Long.parseLong(document.getSize())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked(document, holder);
            }
        });

        //in some cases, it will prevent unwanted situations
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicked(document, holder);
            }
        });

        //if true, your checkbox will be selected, else unselected
        holder.checkBox.setChecked(isSelected(document));

        holder.itemView.setBackgroundResource(isSelected(document) ? R.color.bg_gray : android.R.color.white);
        holder.checkBox.setVisibility(isSelected(document) ? View.VISIBLE : View.GONE);

        holder.checkBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                toggleSelection(document);
                holder.itemView.setBackgroundResource(isChecked ? R.color.bg_gray : android.R.color.white);

                if (mListener != null)
                    mListener.onItemSelected();
            }
        });
    }

    private void onItemClicked(Document document, FileViewHolder holder) {
        if (holder.checkBox.isChecked()) {
            PickerManager.getInstance().remove(document.getPath(), FilePickerConst.FILE_TYPE_DOCUMENT);
            holder.checkBox.setChecked(!holder.checkBox.isChecked(), true);
            holder.checkBox.setVisibility(View.GONE);
        } else if (PickerManager.getInstance().shouldAdd()) {
            PickerManager.getInstance().add(document.getPath(), FilePickerConst.FILE_TYPE_DOCUMENT);
            holder.checkBox.setChecked(!holder.checkBox.isChecked(), true);
            holder.checkBox.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        SmoothCheckBox checkBox;

        ImageView imageView;

        TextView fileNameTextView;

        TextView fileSizeTextView;

        public FileViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            imageView = itemView.findViewById(R.id.file_iv);
            fileNameTextView = itemView.findViewById(R.id.file_name_tv);
            fileSizeTextView = itemView.findViewById(R.id.file_size_tv);
        }
    }
}
