package com.yts.tsbible.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yts.tsbible.R;
import com.yts.tsbible.databinding.ImageMakeSelectItemBinding;
import com.yts.tsbible.viewmodel.ImageMakeSelectViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ImageMakeSelectAdapter extends RecyclerView.Adapter {
    private List<Integer> mImageIdList;

    public ImageMakeSelectAdapter(List<Integer> imageIdList) {
        mImageIdList = imageIdList;
    }

    public void setImageIdList(List<Integer> imageIdList) {
        this.mImageIdList = imageIdList;
    }


    @Override
    public long getItemId(int position) {
        return mImageIdList.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageMakeSelectItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_image_make_select, parent, false);
        return new ImageMakeSelectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ImageMakeSelectViewHolder holder = (ImageMakeSelectViewHolder) viewHolder;
        int id = mImageIdList.get(position);
        ImageMakeSelectViewModel model = new ImageMakeSelectViewModel();
        model.setDrawableId(id);
        model.setPosition(position);
        holder.setViewModel(model);
    }

    @Override
    public int getItemCount() {
        if (mImageIdList != null) {
            return mImageIdList.size();
        }
        return 0;
    }

    private class ImageMakeSelectViewHolder extends RecyclerView.ViewHolder {
        private ImageMakeSelectItemBinding binding;

        public ImageMakeSelectViewHolder(@NonNull ImageMakeSelectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(ImageMakeSelectViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}


