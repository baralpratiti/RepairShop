package com.depauw.repairshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.depauw.repairshop.database.RepairWithVehicle;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<RepairWithVehicle> repairWithVehicles;

    public CustomAdapter(Context context, List<RepairWithVehicle> repairWithVehicles)
    {
        this.context = context;
        this.repairWithVehicles = repairWithVehicles;
    }

    @Override
    public int getCount()
    {
        return repairWithVehicles.size();
    }

    @Override
    public Object getItem(int i)
    {
        return repairWithVehicles.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup)
    {
        if(v == null)
        {
            v = LayoutInflater.from(context).inflate(R.layout.listview_results_row,viewGroup,false);
        }

        RepairWithVehicle repairWithVehicle = repairWithVehicles.get(i);
        TextView yearMakeModel = v.findViewById(R.id.text_year_make_model);
        TextView repairDate = v.findViewById(R.id.text_repair_date);
        TextView repairCost = v.findViewById(R.id.text_repair_cost);
        TextView repairDescription = v.findViewById(R.id.text_repair_description);
        yearMakeModel.setText(repairWithVehicle.getVehicle().getMakeModel());
        repairDate.setText(repairWithVehicle.getRepair().getDate());
        repairCost.setText(String.valueOf(repairWithVehicle.getRepair().getPrice()));
        repairDescription.setText(repairWithVehicle.getRepair().getDescription());

        return v;
    }
}
