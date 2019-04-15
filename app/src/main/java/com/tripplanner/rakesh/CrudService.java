package com.tripplanner.rakesh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CrudService extends AppCompatActivity {
    EditText sNo;
    EditText tripStartDate;
    EditText VehicleNumber;
    EditText agent;
    EditText ctNo;
    EditText factory;
    EditText driverName;
    EditText driverNum;
    EditText billNum;
    EditText billDate;
    EditText dueDate;
    EditText amount;
    Spinner paymentStatusspinner;
    EditText receivedDate;
    EditText discount;
    EditText diesel;
    EditText advance;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sNo = (EditText) findViewById(R.id.serialNoET);
        String sNoValue = sNo.getText().toString();
        Toast.makeText(getApplicationContext(),"Saving Data....."+sNoValue,Toast.LENGTH_LONG).show();

    }

    public void saveData() {
        sNo = (EditText) findViewById(R.id.serialNoET);
        String sNoValue = sNo.getText().toString();

        tripStartDate = (EditText) findViewById(R.id.selectDate);
        String tripStartDateVal = tripStartDate.getText().toString();

        VehicleNumber = (EditText) findViewById(R.id.VehicleNumberET);
        String vehicleNumberValue = VehicleNumber.getText().toString();

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

        paymentStatusspinner = (Spinner) findViewById(R.id.paymentStatusspinner);
        String paymentStatusVal = paymentStatusspinner.getSelectedItem().toString();

        receivedDate = (EditText) findViewById(R.id.receivedDateET);
        String receivedDateVal = receivedDate.getText().toString();

        discount = (EditText) findViewById(R.id.discountET);
        String discountVal = discount.getText().toString();

        diesel = (EditText) findViewById(R.id.dieselET);
        String dieselVal = diesel.getText().toString();

        advance = (EditText) findViewById(R.id.advanceET);
        String advanceVal = advance.getText().toString();
        dbHelper = new DBHelper(this);
        dbHelper.insertTripData(tripStartDateVal, vehicleNumberValue, ctNoVal, factoryVal, agentVal, driverNameVal, driverNumVal, billNumVal, billDateVal, dueDateVal, amountVal, paymentStatusVal, receivedDateVal, discountVal, dieselVal, advanceVal);

    }
}
