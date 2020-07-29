package com.abiarz.pooyaadmin.add_guard_dir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.abiarz.pooyaadmin.R;
import com.abiarz.pooyaadmin.bus.DeleteGuardBus;
import com.abiarz.pooyaadmin.bus.EditGuardBus;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class adapterAdmin extends RecyclerView.Adapter<adapterAdmin.viewholder> {
    private List<datamodelAdmin> datamodels;
    private Context context;

    public adapterAdmin(Context context, List<datamodelAdmin> datamodels){
        this.context=context;
        this.datamodels=datamodels;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guard_list,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(final viewholder holder, final int position) {

        holder.name_value.setText(datamodels.get(position).getName());
        holder.family_value.setText(datamodels.get(position).getFamily());
        holder.shift_value.setText(datamodels.get(position).getShift());
        holder.user_value.setText(datamodels.get(position).getUser_name());
        holder.pass_value.setText(datamodels.get(position).getPassword());
        holder.mobile_value.setText(datamodels.get(position).getPhone());

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EditGuardBus(datamodels.get(position).getId(),datamodels.get(position).getName(),datamodels.get(position).getFamily(),datamodels.get(position).getShift(),datamodels.get(position).getUser_name(),datamodels.get(position).getPassword(),datamodels.get(position).getPhone()));
            }
        });


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DeleteGuardBus(datamodels.get(position).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return datamodels.size();
    }


    public class viewholder extends RecyclerView.ViewHolder{
        TextView name_value,family_value,shift_value,user_value,pass_value,mobile_value,btn_edit,btn_delete;
        public viewholder(View itemView) {
            super(itemView);
            name_value=itemView.findViewById(R.id.name_value);
                    family_value=itemView.findViewById(R.id.family_value);
            shift_value=itemView.findViewById(R.id.shift_value);
                    user_value=itemView.findViewById(R.id.user_value);
            pass_value=itemView.findViewById(R.id.pass_value);
                    mobile_value=itemView.findViewById(R.id.mobile_value);
            btn_edit=itemView.findViewById(R.id.btn_edit);
            btn_delete=itemView.findViewById(R.id.btn_delete);
        }
    }

}
