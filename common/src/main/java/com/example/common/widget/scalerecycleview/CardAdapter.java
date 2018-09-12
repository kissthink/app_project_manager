package com.example.common.widget.scalerecycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.common.R;
import com.example.common.base.BaseApplication;
import com.example.common.sutils.utils.ResourceUtil;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * card适配器，缩放recycleview使用的适配器
 * Created by zhangyinlei on 2018/3/24 17:55
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<String> mList;
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public CardAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image_view, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        Glide.with(BaseApplication.application)
                .load(mList.get(position))
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(ResourceUtil.getDimen(R.dimen.x20))))
                .transition(withCrossFade())
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.view_image_view);
        }

    }

}