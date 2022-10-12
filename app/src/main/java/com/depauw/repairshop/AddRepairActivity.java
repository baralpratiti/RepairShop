package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Repair;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddRepairBinding;

import java.util.Calendar;
import java.util.List;

public class AddRepairActivity extends AppCompatActivity

{

    private ActivityAddRepairBinding binding;

    private View.OnClickListener button_add_repair_clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            String date = binding.edittextRepairDate.getText().toString();
            Float cost = Float.valueOf(binding.edittextRepairCost.getText().toString());
            String description = binding.edittextRepairDescription.getText().toString();
            Vehicle myVehicle = (Vehicle)binding.spinnerVehicles.getSelectedItem();
            Repair myRepair = new Repair(date, cost, description,myVehicle.getId());
            DBHelper helper = DBHelper.getInstance(AddRepairActivity.this);

            long result = helper.insertRepair(myRepair);

            if(result >= 0)
            {
                Toast.makeText(AddRepairActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    };

    private DatePickerDialog.OnDateSetListener datepicker_repair_dateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
        {
            String strMonth = String.format("%02d", month);
            String strDay = String.format("%02d",dayOfMonth);
            String result = String.valueOf(year) + "-" + String.valueOf(strMonth) + "-" + String.valueOf(strDay);

            binding.edittextRepairDate.setText(result);
        }
    };

    private View.OnClickListener repair_date_clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog picker = new DatePickerDialog(AddRepairActivity.this,datepicker_repair_dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            picker.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRepairBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent theIntent = getIntent();
        DBHelper helper = DBHelper.getInstance(this);
        List<Vehicle> vehicles = helper.getVehicles();
        ArrayAdapter<Vehicle> myAdapter = new ArrayAdapter<Vehicle>(this, android.R.layout.simple_list_item_1,vehicles);
        binding.spinnerVehicles.setAdapter(myAdapter);
        binding.edittextRepairDate.setOnClickListener(repair_date_clickListener);
        binding.buttonAddRepair.setOnClickListener(button_add_repair_clickListener);

    }

}