package com.yts.tsbible.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yts.tsbible.R;
import com.yts.tsbible.databinding.ChapterItemBinding;
import com.yts.tsbible.databinding.LabelItemBinding;
import com.yts.tsbible.viewmodel.ChapterViewModel;
import com.yts.tsbible.viewmodel.LabelViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ChapterSelectAdapter extends RecyclerView.Adapter {
    private List<String> mChapterList;

    public ChapterSelectAdapter(List<String> chapterList) {
        mChapterList = chapterList;
    }

    public void setChapterList(List<String> chapterList) {
        this.mChapterList = chapterList;
        notifyDataSetChanged();
    }



    @Override
    public long getItemId(int position) {
        return mChapterList.get(position).hashCode();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChapterItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_chapter_item_select, parent, false);
        return new ChapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ChapterViewHolder holder = (ChapterViewHolder) viewHolder;
        String chapter = mChapterList.get(position);
        ChapterViewModel model = new ChapterViewModel();
        model.setChapter(chapter);
        holder.setViewModel(model);
    }

    @Override
    public int getItemCount() {
        if (mChapterList != null) {
            return mChapterList.size();
        }
        return 0;
    }

    private class ChapterViewHolder extends RecyclerView.ViewHolder {
        private ChapterItemBinding binding;

        public ChapterViewHolder(@NonNull ChapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(ChapterViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}

