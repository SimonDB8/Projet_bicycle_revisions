package com.example.projet_bicycle_revisions.adapter;


import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;



public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView bikeTextView;
        TextView desTextView;
        ViewHolder(View itemView) {
            super(itemView);
            bikeTextView = itemView.findViewById(R.id.bikeRecyclerView);
            desTextView = itemView.findViewById(R.id.problemRecyclerView);
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(BikesEntity.class)) {
            holder.bikeTextView.setText(((BikesEntity) item).getTypeBike());
            holder.desTextView.setText(((BikesEntity)item).getDescriptionBike());
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof BikesEntity) {
                        return ((BikesEntity) mData.get(oldItemPosition)).getId().equals(((BikesEntity) data.get(newItemPosition)).getId());
                    }

                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof BikesEntity) {
                        BikesEntity newBike = (BikesEntity) data.get(newItemPosition);
                        BikesEntity oldBike = (BikesEntity) mData.get(newItemPosition);
                        return newBike.getId().equals(oldBike.getId())
                                && Objects.equals(newBike.getDescriptionBike(), oldBike.getDescriptionBike())
                                && newBike.getFirstNameBike().equals(oldBike.getFirstNameBike());
                    }

                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}

