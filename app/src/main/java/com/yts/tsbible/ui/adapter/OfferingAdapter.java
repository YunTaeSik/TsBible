package com.yts.tsbible.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Offering;
import com.yts.tsbible.data.model.User;
import com.yts.tsbible.databinding.OfferingHeaderItemBinding;
import com.yts.tsbible.databinding.OfferingItemBinding;
import com.yts.tsbible.interactor.OfferingCallback;
import com.yts.tsbible.utills.NullFilter;
import com.yts.tsbible.viewmodel.OfferingHeaderViewModel;
import com.yts.tsbible.viewmodel.OfferingViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class OfferingAdapter extends RecyclerView.Adapter implements Filterable, OfferingCallback {
    private final int HEADER_TYPE = 0;
    private final int OFFERING_TYPE = 1;

    private List<Object> mOfferingList;
    private List<Object> mOfferingListFilter;

    private boolean isChartVisible;

    public OfferingAdapter(List<Object> offeringList) {
        mOfferingList = offeringList;
        this.mOfferingListFilter = offeringList;
    }

    public void setOfferingList(List<Object> offeringList) {
        this.mOfferingList = offeringList;
        this.mOfferingListFilter = offeringList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mOfferingListFilter.get(position);
        if (item instanceof Offering) {
            return OFFERING_TYPE;
        } else {
            return HEADER_TYPE;
        }
    }

    @Override
    public long getItemId(int position) {
        Object item = mOfferingListFilter.get(position);
        if (item instanceof Offering) {
            return ((Offering) item).getDate();
        } else {
            return position;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == OFFERING_TYPE) {
            OfferingItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_offering, parent, false);
            return new OfferingViewHolder(binding);
        }
        OfferingHeaderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_offering_header, parent, false);
        return new OfferingHeaderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == OFFERING_TYPE) {
            OfferingViewHolder holder = (OfferingViewHolder) viewHolder;
            OfferingViewModel model = new OfferingViewModel();
            Object item = mOfferingListFilter.get(position);
            if (item instanceof Offering) {
                model.setOffering((Offering) item);
            }
            holder.setViewModel(model);
        } else if (viewType == HEADER_TYPE) {
            OfferingHeaderViewHolder holder = (OfferingHeaderViewHolder) viewHolder;
            OfferingHeaderViewModel model = new OfferingHeaderViewModel();
            Object item = mOfferingListFilter.get(position);
            if (item instanceof User) {
                model.setUser((User) item);
            }
            model.setData(mOfferingListFilter);
            model.setChartVisible(isChartVisible);
            model.setOfferingCallback(this);
            holder.setViewModel(model);
        }
    }

    @Override
    public int getItemCount() {
        if (mOfferingListFilter != null) {
            return mOfferingListFilter.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Object> filterList = new ArrayList<>();

                String search = charSequence.toString();
                if (search.isEmpty()) {
                    filterList = mOfferingList;
                } else {
                    filterList.add(mOfferingList.get(0));
                    for (Object o : mOfferingList) {
                        if (o instanceof Offering) {
                            Offering offering = (Offering) o;
                            String name = NullFilter.check(offering.getName());
                            if (name.toLowerCase().contains(search.toLowerCase())) {
                                filterList.add(offering);
                            }
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mOfferingListFilter = (List<Object>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public void changeChartVisible() {
        isChartVisible = !isChartVisible;
        notifyItemChanged(0);
    }

    @Override
    public void search(CharSequence charSequence) {
        getFilter().filter(charSequence);
    }

    private class OfferingHeaderViewHolder extends RecyclerView.ViewHolder {
        private OfferingHeaderItemBinding binding;

        public OfferingHeaderViewHolder(@NonNull OfferingHeaderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(OfferingHeaderViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }

    private class OfferingViewHolder extends RecyclerView.ViewHolder {
        private OfferingItemBinding binding;

        public OfferingViewHolder(@NonNull OfferingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(OfferingViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}

