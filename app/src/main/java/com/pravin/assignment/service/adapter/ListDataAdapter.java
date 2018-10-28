package com.pravin.assignment.service.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pravin.assignment.R;
import com.pravin.assignment.databinding.ItemDataBinding;
import com.pravin.assignment.service.model.FeedModel;
import com.pravin.assignment.viewmodel.DataListViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter to show the data in RecyclerView
 *
 */
public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.DataViewHolder> {
    private List<FeedModel> feed_list = new ArrayList<>();
    public ListDataAdapter() {

    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,
                new FrameLayout(parent.getContext()), false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        FeedModel dataModel = feed_list.get(position);
        holder.setViewModel(new DataListViewModel(dataModel));
    }

    @Override
    public int getItemCount() {
        return this.feed_list.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull DataViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull DataViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    /*
    Method to refresh the list with new data
     */
    public void updateDataInRecyclerView(@Nullable ArrayList<FeedModel> data) {
        this.feed_list.clear();
        if (data != null && !data.isEmpty()) {
            this.feed_list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)
                        .error(R.mipmap.ic_default_image))
                .load(imageUrl)
                .into(imageView);
    }


    static class DataViewHolder extends RecyclerView.ViewHolder {

        ItemDataBinding binding;

        DataViewHolder(View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (binding != null) {
                binding.unbind();
            }
        }

        void setViewModel(DataListViewModel viewModel) {
            if (binding != null) {
                binding.setViewmodel(viewModel);
            }
        }
    }
}
