package com.example.projectinrealm.events;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectinrealm.R;
import java.util.List;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.ViewHolder> {
    private static final String TAG = "alertMessage";
    private List<eventModel> eventModelList;
    private Context context;

    public eventAdapter(List<eventModel> eventModelList,Context context){
        this.eventModelList=eventModelList;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        eventModel model=eventModelList.get(position);
        holder.eventName.setText(model.getEventName());
        holder.eventDate.setText(model.getEventDate());
        holder.eventTime.setText(model.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UpdateEvent.class);
                intent.putExtra("eventName",model.getEventName());
                intent.putExtra("eventDate",model.getEventDate());
                intent.putExtra("Description",model.getDescription());
                intent.putExtra("id",model.getId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventName,eventDate,eventTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName=itemView.findViewById(R.id.eventNameId);
            eventDate=itemView.findViewById(R.id.eventDateId);
            eventTime=itemView.findViewById(R.id.eventTimeId);
        }
    }
}
