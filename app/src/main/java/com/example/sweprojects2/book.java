package com.example.sweprojects2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

public class book extends AppCompatActivity {
    Button btn_add, btn_view;
    EditText service_id, staff_id, date_id, time_id;
    ListView lv_AppointmentList;
    ArrayAdapter<bookO> appointmentArrayAdapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize UI components from layout
        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        service_id = findViewById(R.id.service_id); // ID for service input
        staff_id = findViewById(R.id.staff_id); // ID for staff input
        date_id = findViewById(R.id.date_id); // ID for date input
        time_id = findViewById(R.id.time_id); // ID for time input
        lv_AppointmentList = findViewById(R.id.lv_AppointmentList); // List view for displaying appointments
        dbHelper = new DBHelper(book.this);

        //showAppointmentsOnListView(dbHelper);

        btn_view.setOnClickListener(v -> {
            showAppointmentsOnListView(dbHelper);
        });

        btn_add.setOnClickListener(v -> {
            try {
                String service = service_id.getText().toString(); // Assuming service_id also holds service information
                int staffID = Integer.parseInt(staff_id.getText().toString());
                String date = date_id.getText().toString();
                String time = time_id.getText().toString();

                // Assuming Appointment constructor matches these types (adjust if constructor parameters differ)
                bookO appointment = new bookO(-1, -1, staffID, date, time, "", service);
                dbHelper.addAppointment(appointment);
                Toast.makeText(book.this, "Appointment Added", Toast.LENGTH_SHORT).show();

                // Clear the text fields after adding appointment
                service_id.setText("");
                staff_id.setText("");
                date_id.setText("");
                time_id.setText("");

                if (lv_AppointmentList.getAdapter() != null) {
                    appointmentArrayAdapter = new ArrayAdapter<>(book.this,
                            android.R.layout.simple_list_item_1, dbHelper.getAllAppointments());
                    lv_AppointmentList.setAdapter(appointmentArrayAdapter);
                }

            } catch (Exception e) {
                Toast.makeText(book.this, "Enter Valid input", Toast.LENGTH_SHORT).show();
            }
        });


        lv_AppointmentList.setOnItemClickListener((adapterView, view, i, l) -> {
            bookO clickedAppointment = (bookO) adapterView.getItemAtPosition(i);
            dbHelper.deleteOneAppointment(clickedAppointment);
            if (lv_AppointmentList.getAdapter() != null) {
                appointmentArrayAdapter = new ArrayAdapter<>(book.this,
                        android.R.layout.simple_list_item_1, dbHelper.getAllAppointments());
                lv_AppointmentList.setAdapter(appointmentArrayAdapter);
            }
            Toast.makeText(book.this, "Deleted " + clickedAppointment.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    private void showAppointmentsOnListView(DBHelper dbHelper) {
        if (lv_AppointmentList.getAdapter() != null) {
            // Appointments are already shown, hide them
            lv_AppointmentList.setAdapter(null);
        } else {
            // Appointments are not shown, show them
            appointmentArrayAdapter = new ArrayAdapter<>(book.this,
                    android.R.layout.simple_list_item_1, dbHelper.getAllAppointments());
            lv_AppointmentList.setAdapter(appointmentArrayAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type staff ID to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // onQueryTextSubmit method will be called when user types some character(s) and presses Enter
            @Override
            public boolean onQueryTextSubmit(String s) {
                // Assuming DBHelper instance is initialized in the Activity as 'dbHelper'
                try {
                    int staffID = Integer.parseInt(s);
                    List<bookO> searchResult = dbHelper.searchAppointmentsByStaffID(staffID);
                    if (searchResult.isEmpty()) {
                        Toast.makeText(book.this, "No appointments found!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        appointmentArrayAdapter = new ArrayAdapter<>(book.this,
                                android.R.layout.simple_list_item_1, searchResult);
                        lv_AppointmentList.setAdapter(appointmentArrayAdapter);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(book.this, "Invalid input. Please enter a valid staff ID.",
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            // onQueryTextChange method will be called once user types a character
            @Override
            public boolean onQueryTextChange(String s) {
                return false; // If you want to filter as the user types, implement filtering logic here
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle search icon click
        Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
        return true;
    }
}

