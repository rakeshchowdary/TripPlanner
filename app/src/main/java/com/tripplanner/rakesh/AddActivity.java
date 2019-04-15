package com.tripplanner.rakesh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    ProgressDialog progress;

    boolean insertResponse;
    EditText simpleDatePicker;
    EditText billDate;
    EditText dueDate;
    EditText receivedDate;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;

    Button saveDataBtn;
    DatePickerDialog datePickerDialog;
    //DBHelper mydb;
    private ListView obj;
    CrudService crudService;

    EditText sNo;
    EditText tripStartDate;
    EditText VehicleNumber;
    EditText agent;
    EditText ctNo;
    EditText factory;
    EditText driverName;
    EditText driverNum;
    EditText billNum;
    EditText billDateDb;
    EditText dueDateDb;
    EditText amount;
    Spinner paymentStatusspinner;
    EditText receivedDateDb;
    EditText discount;
    EditText diesel;
    EditText advance;





    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);

        //add date pickers
        addDatePickers();

        //add Droppdown items
        addSpinner();

        //save data to db
        saveData();



    }



    private void saveData() {

        dbHelper = new DBHelper(this);
        progress = new ProgressDialog(this);
        saveDataBtn = findViewById(R.id.saveinfobtn);

        saveDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*crudService = new CrudService();
                crudService.saveData();*/
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

                if (TextUtils.isEmpty(driverName.getText())) {
                    driverName.setError("Driver Name Is Required!");
                    Toast.makeText(getApplicationContext(), "Required Fields Are Missing...", Toast.LENGTH_LONG).show();

                } else if(TextUtils.isEmpty(sNo.getText())){
                    sNo.setError("Serial Number is required!!");
                    Toast.makeText(getApplicationContext(), "Required Fields Are Missing...", Toast.LENGTH_LONG).show();


                }else if(TextUtils.isEmpty(tripStartDate.getText())){
                    tripStartDate.setError("Trip StartDate is needed!");
                    Toast.makeText(getApplicationContext(), "Required Fields Are Missing...", Toast.LENGTH_LONG).show();


                }else if(TextUtils.isEmpty(VehicleNumber.getText())){
                    VehicleNumber.setError("Vehicle Number is required!!");
                    Toast.makeText(getApplicationContext(), "Required Fields Are Missing...", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(ctNo.getText())){
                    ctNo.setError("CT No is required!!");
                }else if(TextUtils.isEmpty(factory.getText())){
                    factory.setError("Factory is Required!!");
                }else if(TextUtils.isEmpty(agent.getText())){
                    agent.setError("agent is required!!");
                }else if(TextUtils.isEmpty(driverNum.getText())){
                    driverNum.setError("Driver Contact is required!!");
                }else if(TextUtils.isEmpty(billNum.getText())){
                    billNum.setError("Bill Number is required!!");
                }else if(TextUtils.isEmpty(dueDate.getText())){
                    dueDate.setError("Due Date Is required!!");
                }else if(TextUtils.isEmpty(amount.getText())){
                    amount.setError("Amount is required!!");

                }else if(TextUtils.isEmpty(diesel.getText())){
                    diesel.setError("Diesel is required!!");
                }
                else {

                    // Toast.makeText(getApplicationContext(), "Saving Data....." + driverNameVal+insertResponse, Toast.LENGTH_LONG).show();
                    insertResponse = dbHelper.insertTripData(tripStartDateVal, vehicleNumberValue, ctNoVal, factoryVal, agentVal, driverNameVal, driverNumVal, billNumVal, billDateVal, dueDateVal, amountVal, paymentStatusVal, receivedDateVal, discountVal, dieselVal, advanceVal);
//                progress.setTitle("Saving Data!!");
//                progress.setMessage("Please Wait.....");
//                progress.setCancelable(true);
//                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progress.setMax(100);
//                progress.show();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (insertResponse) {

                        Toast.makeText(getApplicationContext(), "Done..", Toast.LENGTH_LONG).show();
                        Intent inte = new Intent(AddActivity.this, DisplayData.class);

                        startActivity(inte);
                        //  progress.dismiss();

                    } else {
                        // progress.dismiss();
                        Toast.makeText(getApplicationContext(), "Something Went Wrong Please Try After Sometime...", Toast.LENGTH_LONG).show();

                    }
                }


            }
        });
    }



    private void addSpinner() {

        Spinner dialog = findViewById(R.id.paymentStatusspinner);
        String[] items = new String[]{"select...", "Received", "Due"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        dialog.setAdapter(adapter);

    }





    private void addDatePickers() {
        simpleDatePicker = (EditText) findViewById(R.id.selectDate);
        billDate = (EditText) findViewById(R.id.BilldateET);
        dueDate = (EditText) findViewById(R.id.duedateET);
        receivedDate = (EditText) findViewById(R.id.receivedDateET);
        saveDataBtn = (Button) findViewById(R.id.saveinfobtn);


        simpleDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                simpleDatePicker.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

        billDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                billDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dueDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        receivedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                receivedDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
    }
}
