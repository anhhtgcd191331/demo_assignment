package com.example.demo_assignment.trips;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_assignment.R;

import java.util.ArrayList;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.MyViewHolder> implements Filterable{
    private Context context;
    private Activity activity;
    public List<Trip> trips = new ArrayList<>();
    private List<Trip> tripFind;
    private ArrayList trip_id, trip_name,trip_destination,trip_date,trip_require,trip_description;
    String username;
    Animation translate_anim;

    TripsAdapter(Activity activity, Context context,List<Trip> trips){
        this.activity = activity;
        this.context = context;
        this.trips = trips;
        this.tripFind = trips;
    }

    @NonNull
    @Override
    public TripsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Trip trip = trips.get(position);
        holder.trip_id_txt.setText(String.valueOf(trip.getId()));
        holder.name_trip_txt.setText(String.valueOf(trip.getName()));
        holder.destination_trip_txt.setText(String.valueOf(trip.getDestination()));
        holder.date_trip_txt.setText(String.valueOf(trip.getDate()));
        holder.require_txt.setText(String.valueOf(trip.getRequire()));
        holder.destination_trip_txt.setText(String.valueOf(trip.getDescription()));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateTrip.class);
                intent.putExtra("trip_id", String.valueOf(trip.getId()));
                intent.putExtra("name", String.valueOf(trip.getName()));
                intent.putExtra("destination", String.valueOf(trip.getDestination()));
                intent.putExtra("date", String.valueOf(trip.getDate()));
                intent.putExtra("require", String.valueOf(trip.getRequire()));
                intent.putExtra("des", String.valueOf(trip.getDescription()));
                intent.putExtra("username",String.valueOf(trip.getUsername()));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_txt, name_trip_txt, destination_trip_txt,date_trip_txt,require_txt,description_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            name_trip_txt = itemView.findViewById(R.id.name_trip_txt);
            destination_trip_txt = itemView.findViewById(R.id.destination_trip_txt);
            date_trip_txt = itemView.findViewById(R.id.date_trip_txt);
            require_txt = itemView.findViewById(R.id.require_txt);
            description_txt = itemView.findViewById(R.id.destination_trip_txt);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    trips = tripFind;
                }else {
                    List<Trip> list = new ArrayList<>();
                    for(Trip trip:tripFind){
                        if(trip.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(trip);
                        }
                    }
                    trips = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = trips;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                trips = (List<Trip>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}

