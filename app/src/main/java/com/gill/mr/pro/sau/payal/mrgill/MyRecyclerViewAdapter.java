package com.gill.mr.pro.sau.payal.mrgill;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private Context c;
    ArrayList<ListModel> list_model;
    JSONArray array;

    public MyRecyclerViewAdapter(Context c, ArrayList<ListModel> list_model) {
        this.c = c;
        this.list_model=list_model;

    }

    public  void add(ListModel grid_model)
    {
        list_model.add(grid_model);
        notifyDataSetChanged();
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.listitem, parent, false);
        return new MyRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
//            JSONObject jsonObjec=array.getJSONObject(position);
            ListModel model=list_model.get(position);
          /*  holder.text_name.setText(jsonObjec.getString("name"));
            holder.text_descriptoin.setText(jsonObjec.getString("description"));
            holder.text_link.setText(jsonObjec.getString("link"));
            Picasso.get().load(jsonObjec.getString("url"))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);*/

            holder.text_name.setText(model.getName());
            holder.text_descriptoin.setText(model.getDescription());
            holder.text_link.setText(model.getUrl());
            Picasso.get().load("http://192.168.1.200/Mr.Gill/public/uploads/"+model.getPicture())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
//        Picasso.get().load("http://192.168.1.200/Mr.Gill/public/uploads/"+o.getString("picture")).into(holder.course_picture);

    }

    @Override
    public int getItemCount() {
        return list_model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView text_link,text_name,text_descriptoin;

        private ViewHolder(View v){
            super(v);
            imageView = v.findViewById(R.id.course_picture);
            text_link = v.findViewById(R.id.course_link);
            text_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    text_link.setMovementMethod(LinkMovementMethod.getInstance());

                }
            });
            text_name = v.findViewById(R.id.course_title);
            text_descriptoin = v.findViewById(R.id.course_description);
        }

    }

}