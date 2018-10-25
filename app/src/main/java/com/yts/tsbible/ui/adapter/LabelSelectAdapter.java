package com.yts.tsbible.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.databinding.BibleItemBinding;
import com.yts.tsbible.databinding.LabelItemBinding;
import com.yts.tsbible.viewmodel.BibleViewModel;
import com.yts.tsbible.viewmodel.LabelViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class LabelSelectAdapter extends RecyclerView.Adapter {
    private List<String> mLabelList;

    public LabelSelectAdapter(List<String> labelList) {
        mLabelList = labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.mLabelList = labelList;
        notifyDataSetChanged();
    }



    @Override
    public long getItemId(int position) {
        return mLabelList.get(position).hashCode();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LabelItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_label_item_select, parent, false);
        return new LabelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        LabelViewHolder holder = (LabelViewHolder) viewHolder;
        String label = mLabelList.get(position);
        LabelViewModel model = new LabelViewModel();
        model.setLabel(label);
        holder.setViewModel(model);
    }

    @Override
    public int getItemCount() {
        if (mLabelList != null) {
            return mLabelList.size();
        }
        return 0;
    }

    private class LabelViewHolder extends RecyclerView.ViewHolder {
        private LabelItemBinding binding;

        public LabelViewHolder(@NonNull LabelItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(LabelViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}

