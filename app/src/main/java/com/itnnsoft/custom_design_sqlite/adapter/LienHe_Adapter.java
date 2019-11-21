package com.itnnsoft.custom_design_sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itnnsoft.custom_design_sqlite.R;
import com.itnnsoft.custom_design_sqlite.model.LienHe;

import java.util.List;

public class LienHe_Adapter extends ArrayAdapter<LienHe> {
    private Context context;
    private int resoure;
    private List<LienHe> listLienhe;

    public LienHe_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<LienHe> objects) {
        super(context, resource, objects);
        this.context= context;
        this.resoure=resource;
        this.listLienhe =objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.lienhe_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txt_name = (TextView)convertView.findViewById(R.id.txt_name);
            viewHolder.txt_number = (TextView)convertView.findViewById(R.id.txt_number);
            viewHolder.img_gt = (ImageView) convertView.findViewById(R.id.img_gt);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LienHe lienHe = listLienhe.get(position);
        viewHolder.txt_name.setText(lienHe.getTen());
        viewHolder.txt_number.setText(lienHe.getNumber());

        if(lienHe.getGioi_tinh().compareTo("Nu")==0){
            viewHolder.img_gt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_nu));
        }else {
            viewHolder.img_gt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_nam));
        }

        return convertView;
    }

    public class ViewHolder{

        private TextView txt_name;
        private TextView txt_number;
        private ImageView img_gt;

    }
}
