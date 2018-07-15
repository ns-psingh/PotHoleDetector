package com.example.premal2.potholedetector;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by premal2 on 7/15/2018.
 */

public class ArtistList extends ArrayAdapter<acceleration> {

    private Activity context;
    private List<acceleration> artistList;
    public ArtistList(Activity context,List<acceleration> artistList)
    {
        super(context,R.layout.list_layout,artistList);
        this.context=context;
        this.artistList=artistList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);
        TextView address= (TextView)  listViewItem.findViewById(R.id.address);
        TextView latitude= (TextView) listViewItem.findViewById(R.id.latitude);
        TextView longitude= (TextView) listViewItem.findViewById(R.id.longitude);
        TextView time= (TextView) listViewItem.findViewById(R.id.time);
        TextView xa=(TextView) listViewItem.findViewById(R.id.acceleration_x);
        TextView ya= (TextView) listViewItem.findViewById(R.id.acceleration_y);
        TextView za= (TextView) listViewItem.findViewById(R.id.acceleration_z);
        acceleration x= artistList.get(position);
        address.setText("Location: "+x.getAddress());
        latitude.setText("Latitude: "+x.getLatitude()+"");
        longitude.setText("Longitude: "+x.getLongitude()+"");
        time.setText("Time and date: "+x.getTime()+"");
        xa.setText("Aceeleration in x-direction: "+x.getX()+"");
        ya.setText("Acceleration in y-direction: "+x.getY()+"");
        za.setText("Acceleration in z-direction: "+x.getZ()+"");
        return listViewItem;
     }
}
