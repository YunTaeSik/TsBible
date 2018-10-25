package com.yts.tsbible.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.databinding.BibleItemBinding;
import com.yts.tsbible.viewmodel.BibleViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BibleAdapter extends RecyclerView.Adapter {
    private List<Bible> mBibleList;
    private int textSize;
    private boolean isAddSentence = false;

    public BibleAdapter(List<Bible> bibleList) {
        mBibleList = bibleList;
    }

    public void setBibleList(List<Bible> bibleList) {
        this.mBibleList = bibleList;
        notifyDataSetChanged();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        notifyDataSetChanged();
    }

    public void setAddSentence(boolean addSentence) {
        isAddSentence = addSentence;
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(mBibleList.get(position).getIdx() + (textSize + 1));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BibleItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_bible, parent, false);
        return new BibleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        BibleViewHolder holder = (BibleViewHolder) viewHolder;
        Bible bible = mBibleList.get(position);
        BibleViewModel model = new BibleViewModel();
        model.setPosition(position);
        model.setBible(bible);
        model.setSize(textSize);
        model.setIsSentence(isAddSentence);
        holder.setViewModel(model);
    }

    @Override
    public int getItemCount() {
        if (mBibleList != null) {
            return mBibleList.size();
        }
        return 0;
    }

    private class BibleViewHolder extends RecyclerView.ViewHolder {
        private BibleItemBinding binding;

        public BibleViewHolder(@NonNull BibleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(BibleViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}
