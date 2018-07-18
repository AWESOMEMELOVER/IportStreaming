package com.example.micka.camerapp.Adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.micka.camerapp.Entity.Camera;
import com.example.micka.camerapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CameraFlowAdapter extends BaseAdapter{

    private ArrayList<Camera> data;
    private AppCompatActivity activity;

    public CameraFlowAdapter(AppCompatActivity context,ArrayList<Camera> objects){
        this.data = objects;
        this.activity=context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_camera_view, null, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cameraText.setText(data.get(position).getId());
        Picasso.with(activity).load(data.get(position).getUri()).into(viewHolder.cameraImage);

        return convertView;
    }

    private class ViewHolder{
        private TextView cameraText;
        private ImageView cameraImage;

        public ViewHolder(View v){
            cameraText = (TextView) v.findViewById(R.id.name);
            cameraImage = (ImageView) v.findViewById(R.id.image);
        }
    }
}
