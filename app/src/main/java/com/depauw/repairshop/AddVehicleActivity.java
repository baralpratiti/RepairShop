package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddVehicleBinding;
import com.depauw.repairshop.databinding.ActivityMainBinding;

public class AddVehicleActivity extends AppCompatActivity {



    private ActivityAddVehicleBinding binding;

    private View.OnClickListener button_add_vehicle_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            int year = Integer.valueOf(binding.edittextYear.getText().toString());
            float price = Float.valueOf(binding.edittextPrice.getText().toString());
            String makeModel = binding.edittextMakeModel.getText().toString();
            int isNew = 0;
            if(binding.checkboxIsNew.isChecked())
            {
                isNew =1;
            }
            else
            {
                isNew = 0;
            }

            Vehicle newVehicle = new Vehicle(year,price,makeModel,isNew);
            DBHelper helper = DBHelper.getInstance(AddVehicleActivity.this);

            long result = helper.insertVehicle(newVehicle);

            if (result >=0)
            {
                Toast.makeText(AddVehicleActivity.this, "A vehicle has been added", Toast.LENGTH_SHORT).show();
            }

            finish ();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent myIntent = getIntent();
        binding.buttonAddVehicle.setOnClickListener(button_add_vehicle_clickListener);

    }
}