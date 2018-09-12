package com.xywg.pm.projectmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;
import com.xywg.pm.projectmanager.bean.UserInfoBean;

import java.util.List;

public class OprationAdapter extends BaseAdapter {

    private List<UserInfoBean.OperationBean> list;
    private Context mContext;

    public OprationAdapter(List<UserInfoBean.OperationBean> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tree_list_view, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.id_treenode_icon);
            holder.label = convertView.findViewById(R.id.id_treenode_label);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setVisibility(View.INVISIBLE);
        holder.label.setText(list.get(position).getSoName());
        return convertView;
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView label;
    }

}
