package com.yts.tsbible.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yts.tsbible.R;
import com.yts.tsbible.data.model.History;
import com.yts.tsbible.data.model.User;
import com.yts.tsbible.databinding.HistoryItemBinding;
import com.yts.tsbible.databinding.HomeHeaderItemBinding;
import com.yts.tsbible.viewmodel.HistoryViewModel;
import com.yts.tsbible.viewmodel.HomeHeaderViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter {
    private final int HEADER_TYPE = 0;
    private final int HISTORY_TYPE = 1;

    private List<Object> mHomeList;

    public HomeAdapter(List<Object> homeList) {
        mHomeList = homeList;
    }

    public void setHomeList(List<Object> homeList) {
        this.mHomeList = homeList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mHomeList.get(position);
        if (item instanceof History) {
            return HISTORY_TYPE;
        } else {
            return HEADER_TYPE;
        }
    }

    @Override
    public long getItemId(int position) {
        Object item = mHomeList.get(position);
        if (item instanceof History) {
            return ((History) item).getDate();
        } else {
            return position;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HISTORY_TYPE) {
            HistoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_history, parent, false);
            return new HistoryViewHolder(binding);
        }
        HomeHeaderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_home_header, parent, false);
        return new HomeHeaderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == HISTORY_TYPE) {
            HistoryViewHolder holder = (HistoryViewHolder) viewHolder;
            HistoryViewModel model = new HistoryViewModel();
            Object item = mHomeList.get(position);
            if (item instanceof History) {
                model.setHistory((History) item);
            }
            holder.setViewModel(model);
        } else if (viewType == HEADER_TYPE) {
            HomeHeaderViewHolder holder = (HomeHeaderViewHolder) viewHolder;
            HomeHeaderViewModel model = new HomeHeaderViewModel();
            Object item = mHomeList.get(position);
            if (item instanceof User) {
                model.setUser((User) item);
            }
            holder.setViewModel(model);
        }
    }

    @Override
    public int getItemCount() {
        if (mHomeList != null) {
            return mHomeList.size();
        }
        return 0;
    }

    private class HomeHeaderViewHolder extends RecyclerView.ViewHolder {
        private HomeHeaderItemBinding binding;

        public HomeHeaderViewHolder(@NonNull HomeHeaderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(HomeHeaderViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder {
        private HistoryItemBinding binding;

        public HistoryViewHolder(@NonNull HistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(HistoryViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}
