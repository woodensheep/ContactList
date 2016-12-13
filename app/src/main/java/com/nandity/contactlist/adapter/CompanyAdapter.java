/**
 * 2015-4-1
 */
package com.nandity.contactlist.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nandity.contactlist.R;
import com.nandity.contactlist.entity.CompanyEntity;
import com.nandity.contactlist.utils.Utils;

import java.util.List;


/**
 * @author wcy
 */
public class CompanyAdapter extends BaseAdapter {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_COMPANY = 1;
    private List<CompanyEntity> mCompanyList;

    public CompanyAdapter(List<CompanyEntity> companyList) {
        mCompanyList = companyList;
    }

    @Override
    public int getCount() {
        return mCompanyList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCompanyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(mCompanyList.get(position).getCode())) {
            return TYPE_TITLE;
        } else {
            return TYPE_COMPANY;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEnabled(int position) {
        return !TextUtils.isEmpty(mCompanyList.get(position).getCode());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        IndexViewHolder indexHolder;
        CompanyViewHolder companyHolder;
        switch (getItemViewType(position)) {
            case TYPE_TITLE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_holder_company_index, parent, false);
                    indexHolder = new IndexViewHolder();
                    indexHolder.tvIndex= (TextView) convertView.findViewById(R.id.tv_index);
                    convertView.setTag(indexHolder);
                } else {
                    indexHolder = (IndexViewHolder) convertView.getTag();
                }
                indexHolder.tvIndex.setText(mCompanyList.get(position).getName());
                break;
            case TYPE_COMPANY:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_holder_company, parent, false);
                    companyHolder = new CompanyViewHolder();
                    companyHolder.ivLogo= (ImageView) convertView.findViewById(R.id.iv_logo);
                    companyHolder.tvName= (TextView) convertView.findViewById(R.id.tv_name);
                    convertView.setTag(companyHolder);
                } else {
                    companyHolder = (CompanyViewHolder) convertView.getTag();
                }
                Glide.with(context)
                        .load(Utils.formatLogoUrl(mCompanyList.get(position).getLogo()))
                        .dontAnimate()
                        .placeholder(R.drawable.default_logo)
                        .into(companyHolder.ivLogo);
                companyHolder.tvName.setText(mCompanyList.get(position).getName());
                break;
        }
        return convertView;
    }

    public static class IndexViewHolder {
        public TextView tvIndex;

    }

    public static class CompanyViewHolder {
        public ImageView ivLogo;
        public TextView tvName;

    }
}
