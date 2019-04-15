package com.tripplanner.rakesh;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditInfo extends AppCompatActivity {

    Button cancelBt;

    Button updateBt;

    DBHelper dbHelper;

    boolean updateResponse;

    EditText sNoEt;
    EditText tripStartDate;
    EditText vehicleNumber;
    EditText ctNo;
    EditText factory;
    EditText agent;
    EditText driverName;
    EditText driverNum;
    EditText billNum;
    EditText billDate;
    EditText dueDate;
    EditText amount;
    Spinner paymentStatus;
    EditText receivedDate;
    EditText discount;
    EditText diesel;
    EditText advance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);

        cancelBt = findViewById(R.id.cancelBtn);
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditInfo.this, DisplayData.class);
                startActivity(intent);
            }
        });

       showInformation();

        updateBt = findViewById(R.id.updateBtn);
        dbHelper = new DBHelper(this);

        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
               String id= intent.getStringExtra("SerialNumber");
                tripStartDate = (EditText) findViewById(R.id.selectDate);
                String tripStartDateVal = tripStartDate.getText().toString();

                vehicleNumber = (EditText) findViewById(R.id.VehicleNumberET);
                String vehicleNumberValue = vehicleNumber.getText().toString();

                ctNo = (EditText) findViewById(R.id.CTnoET);
                String ctNoVal = ctNo.getText().toString();

                factory = (EditText) findViewById(R.id.factoryET);
                String factoryVal = factory.getText().toString();

                agent = (EditText) findViewById(R.id.agentET);
                String agentVal = agent.getText().toString();

                driverName = (EditText) findViewById(R.id.drivernameET);
                String driverNameVal = driverName.getText().toString();

                driverNum = (EditText) findViewById(R.id.drivernumET);
                String driverNumVal = driverNum.getText().toString();

                billNum = (EditText) findViewById(R.id.BillnumET);
                String billNumVal = billNum.getText().toString();

                billDate = (EditText) findViewById(R.id.BilldateET);
                String billDateVal = billDate.getText().toString();

                dueDate = (EditText) findViewById(R.id.duedateET);
                String dueDateVal = dueDate.getText().toString();

                amount = (EditText) findViewById(R.id.amountET);
                String amountVal = amount.getText().toString();

                paymentStatus = (Spinner) findViewById(R.id.paymentStatusspinner);
                String paymentStatusVal = paymentStatus.getSelectedItem().toString();

                receivedDate = (EditText) findViewById(R.id.receivedDateET);
                String receivedDateVal = receivedDate.getText().toString();

                discount = (EditText) findViewById(R.id.discountET);
                String discountVal = discount.getText().toString();

                diesel = (EditText) findViewById(R.id.dieselET);
                String dieselVal = diesel.getText().toString();

                advance = (EditText) findViewById(R.id.advanceET);
                String advanceVal = advance.getText().toString();

                updateResponse = dbHelper.updateContact(Integer.valueOf(id),tripStartDateVal, vehicleNumberValue, ctNoVal, factoryVal, agentVal, driverNameVal, driverNumVal, billNumVal, billDateVal, dueDateVal, amountVal, paymentStatusVal, receivedDateVal, discountVal, dieselVal, advanceVal);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (updateResponse) {

                    Toast.makeText(getApplicationContext(), "Update Done..", Toast.LENGTH_LONG).show();
                    Intent inte = new Intent(EditInfo.this, DisplayData.class);

                    startActivity(inte);
                    //  progress.dismiss();

                } else {
                    // progress.dismiss();
                    Toast.makeText(getApplicationContext(), "Something Went Wrong Please Try After Sometime...", Toast.LENGTH_LONG).show();

                }


            }
        });


    }




    private void showInformation() {
        Intent intent = getIntent();

        sNoEt = (EditText) findViewById(R.id.serialNoET);
        sNoEt.setText(intent.getStringExtra("SerialNumber"));

        tripStartDate = (EditText) findViewById(R.id.selectDate);
        tripStartDate.setText(intent.getStringExtra("TripStartDate"));

        vehicleNumber = (EditText) findViewById(R.id.VehicleNumberET);
        vehicleNumber.setText(intent.getStringExtra("VehicleNumber"));

        ctNo = (EditText) findViewById(R.id.CTnoET);
        ctNo.setText(intent.getStringExtra("CtNo"));

        factory = (EditText) findViewById(R.id.factoryET);
        factory.setText(intent.getStringExtra("Factory"));

        agent = (EditText) findViewById(R.id.agentET);
        agent.setText(intent.getStringExtra("Agent"));

        driverName = (EditText) findViewById(R.id.drivernameET);
        driverName.setText(intent.getStringExtra("DriverName"));


        driverNum = (EditText) findViewById(R.id.drivernumET);
        driverNum.setText(intent.getStringExtra("DriverNumber"));

        billNum = (EditText) findViewById(R.id.BillnumET);
        billNum.setText(intent.getStringExtra("BillNumber"));

        billDate = (EditText) findViewById(R.id.BilldateET);
        billDate.setText(intent.getStringExtra("BillDate"));

        dueDate = (EditText) findViewById(R.id.duedateET);
        dueDate.setText(intent.getStringExtra("DueDate"));

        amount = (EditText) findViewById(R.id.amountET);
        amount.setText(intent.getStringExtra("Amount"));

        paymentStatus = (Spinner) findViewById(R.id.paymentStatusspinner);

        String[] items = new String[]{"select...", "Received", "Due"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        paymentStatus.setAdapter(adapter);
        String compareValue = intent.getStringExtra("PaymentStatus");

        int position = 0;
        // Log.d("Payment", "Statues " + paymentStatus.getSelectedItem().toString());
        if ("Due".equalsIgnoreCase(compareValue)) {
            position = 1;

        } else {
            position = 2;
        }


        paymentStatus.setSelection(position);


        receivedDate = (EditText) findViewById(R.id.receivedDateET);
        receivedDate.setText(intent.getStringExtra("ReceivedDate"));

        discount = (EditText) findViewById(R.id.discountET);
        discount.setText(intent.getStringExtra("Discount"));

        diesel = (EditText) findViewById(R.id.dieselET);
        diesel.setText(intent.getStringExtra("Diesel"));

        advance = (EditText) findViewById(R.id.advanceET);
        advance.setText(intent.getStringExtra("Advance"));
    }
}
