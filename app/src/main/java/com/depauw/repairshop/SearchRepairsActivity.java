package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Repair;
import com.depauw.repairshop.database.RepairWithVehicle;
import com.depauw.repairshop.databinding.ActivitySearchRepairsBinding;

import java.util.List;

public class SearchRepairsActivity extends AppCompatActivity

{

    private ActivitySearchRepairsBinding binding;

    CustomAdapter myAdapter;
    List<RepairWithVehicle> mylist;

    private View.OnClickListener button_find_repair_clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
            String search = binding.edittextSearchPhrase.getText().toString();
            DBHelper helper = DBHelper.getInstance(SearchRepairsActivity.this);
            mylist = helper.getRepairsWithVehicle(search);
            myAdapter = new CustomAdapter(SearchRepairsActivity.this,mylist);
            binding.listviewResults.setAdapter(myAdapter);
        }
    };

    private AdapterView.OnItemLongClickListener listview_long_clickListener = new AdapterView.OnItemLongClickListener()

    {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View v, int i, long l) {

            DBHelper helper = DBHelper.getInstance(SearchRepairsActivity.this);
            RepairWithVehicle myRepair = (RepairWithVehicle) adapterView.getItemAtPosition(i);
            Repair thisRepair = myRepair.getRepair();
            String description = thisRepair.getDescription();
            int numRows = helper.deleteVehicle(description);

            if(numRows>0)
            {
                Toast.makeText(SearchRepairsActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
            }

            String search = binding.edittextSearchPhrase.getText().toString();
            List<RepairWithVehicle> myList = helper.getRepairsWithVehicle(search);
            CustomAdapter myAdapter= new CustomAdapter(SearchRepairsActivity.this,myList);
            binding.listviewResults.setAdapter(myAdapter);
            binding.listviewResults.invalidateViews();
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchRepairsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonFindRepairs.setOnClickListener(button_find_repair_clickListener);
        binding.listviewResults.setOnItemLongClickListener(listview_long_clickListener);
    }

}