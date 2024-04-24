package com.example.sweprojects2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "GlamBook.db";
    private static final int DATABASE_VERSION = 1;

    // Tables names
    private static final String Client = "client";
    private static final String Appointment  = "appointment";
    private static final String Staff  = "staff";
    private static final String Service  = "service";

    // Client table column names
    private static final String ClientID = "userID";
    private static final String ClientName = "clientName";
    private static final String Email = "email";
    private static final String PhoneNumber = "phoneNumber";
    private static final String Birthday = "birthday";
    private static final String Password = "password";

    // Appointment table column names
    private static final String AppointmentID = "appointmentID";
    private static final String Date = "date";
    private static final String Time = "time";
    // Staff table column names
    private static final String StaffID = "staffID";
    private static final String StaffName = "staffName";
    private static final String Rating = "rating";
    private static final String Specialty = "specialty";

    // Service table column names
    private static final String ServiceID = "serviceID";
    private static final String ServiceName = "serviceName";
    private static final String Description = "description";
    private static final String Price = "price";

    //constructor
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // create the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create client table
        String createClientTableQuery = "CREATE TABLE " + Client + "("
                + ClientID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ClientName + " TEXT,"
                + Email + " TEXT,"
                + PhoneNumber + " TEXT,"
                + Birthday + " TEXT,"
                + Password + " TEXT" + ")";
        db.execSQL(createClientTableQuery);

        // Create appointment table
        String createAppointmentTableQuery = "CREATE TABLE " + Appointment + "("
                + AppointmentID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ClientID + " INTEGER,"
                + Date + " TEXT,"
                + Time + " TEXT,"
                + StaffID + " INTEGER,"
                + ServiceID + " INTEGER,"
                + StaffName + " TEXT,"
                + "FOREIGN KEY(" + ClientID + ") REFERENCES " + Client + "(" + ClientID + "),"
                + "FOREIGN KEY(" + StaffID + ") REFERENCES " + Staff + "(" + StaffID + "),"
                + "FOREIGN KEY(" + ServiceID + ") REFERENCES " + Service + "(" + ServiceID + ")" + ")";
        db.execSQL(createAppointmentTableQuery);
        // Create staff table
        String createStaffTableQuery = "CREATE TABLE " + Staff + "("
                + StaffID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StaffName + " TEXT,"
                + Rating + " REAL,"
                + Specialty + " TEXT" + ")";
        db.execSQL(createStaffTableQuery);

        // Create service table
        String createServiceTableQuery = "CREATE TABLE " + Service + "("
                + ServiceID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ServiceName + " TEXT,"
                + Description + " TEXT,"
                + Price + " REAL" + ")";
        db.execSQL(createServiceTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Client);
        db.execSQL("DROP TABLE IF EXISTS " + Appointment);
        db.execSQL("DROP TABLE IF EXISTS " + Staff);
        db.execSQL("DROP TABLE IF EXISTS " + Service);
        // Create tables again
        onCreate(db);
    }
    public boolean addClient(String clientName, String email, String phoneNumber, String birthday, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ClientName, clientName);
        values.put(Email, email);
        values.put(PhoneNumber, phoneNumber);
        values.put(Birthday, birthday);
        values.put(Password, password);

        long result = db.insert(Client, null, values);
        db.close(); // Close the database connection
        if(result==-1)
            return false;
        else return true;
    }
    public boolean addAppointment(bookO appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Since appointmentID is auto-incremented, we don't need to put it into ContentValues
        values.put(ClientID, appointment.getClientID());
        values.put(Date, appointment.getDate() != null ? appointment.getDate() : null);
        values.put(Time, appointment.getTime() != null ? appointment.getTime() : null);
        values.put(StaffID, appointment.getStaffID());
        values.put(StaffName, appointment.getStaffName() != null ? appointment.getStaffName() : null);

        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(Appointment, null, values);
        db.close(); // Close database connection

        // Check if insertion was successful
        return result != -1;
    }
    public boolean deleteOneAppointment(bookO appointment) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create SQL delete query
        String queryString = "DELETE FROM " + Appointment + " WHERE " + AppointmentID + " = " + appointment.getAppointmentID();

        // Execute the query
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cursor.close(); // Close the cursor to release resources
            db.close(); // Close the database connection
            return true; // The deletion was successful
        } else {
            cursor.close(); // Close the cursor to release resources
            db.close(); // Close the database connection
            return false; // The deletion failed (e.g., no row found)
        }
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + Client + " WHERE " + Email + "=?";
            cursor = db.rawQuery(query, new String[]{email});

            return cursor.getCount() > 0;
        } finally {
            if (cursor != null)
                cursor.close();

            db.close();
        }
    }
    public boolean loginClient(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + Client + " WHERE " + Email + "=? AND " + Password + "=?";
            cursor = db.rawQuery(query, new String[]{email, password});

            return cursor.getCount() > 0;
        } finally {
            if (cursor != null)
                cursor.close();

            db.close();
        }
    }
    public int getClientId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int clientId = -1;
        try {
            String query = "SELECT " + ClientID + " FROM " + Client + " WHERE " + Email + "=?";
            cursor = db.rawQuery(query, new String[]{email});

            if (cursor.moveToFirst()) {
                clientId = cursor.getInt(cursor.getColumnIndex(ClientID));
            }

            return clientId;
        } finally {
            if (cursor != null)
                cursor.close();

            db.close();
        }
    }
    public List<bookO> getAllAppointments() {
        List<bookO> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + Appointment;
        Cursor cursor = db.rawQuery(queryString, null);
        try {
            if (cursor.moveToFirst()) {
                int appointmentIDIndex = cursor.getColumnIndex(AppointmentID);
                int clientIDIndex = cursor.getColumnIndex(ClientID);
                int staffIDIndex = cursor.getColumnIndex(StaffID);
                int dateIndex = cursor.getColumnIndex(Date);
                int timeIndex = cursor.getColumnIndex(Time);
                int staffNameIndex = cursor.getColumnIndex(StaffName);
                int serviceNameIndex = cursor.getColumnIndex(ServiceName);

                while (!cursor.isAfterLast()) {
                    if (appointmentIDIndex != -1) {
                        int appointmentID = cursor.getInt(appointmentIDIndex);
                        int clientID = cursor.getInt(clientIDIndex);
                        int staffID = cursor.getInt(staffIDIndex);
                        String date = cursor.getString(dateIndex);
                        String time = cursor.getString(timeIndex);
                        String staffName = cursor.getString(staffNameIndex);
                        String serviceName = cursor.getString(serviceNameIndex);

                        bookO newAppointment = new bookO(appointmentID, clientID, staffID, date, time, staffName, serviceName);
                        returnList.add(newAppointment);
                    }
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
            db.close();
        }
        return returnList;
    }

    public List<bookO> searchAppointmentsByStaffID(int clientID) {
        List<bookO> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM " + Appointment + " WHERE " + StaffID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{String.valueOf(clientID)});
        try {
            if (cursor.moveToFirst()) {
                int appointmentIDIndex = cursor.getColumnIndex(AppointmentID);
                int staffIDIndex = cursor.getColumnIndex(StaffID);
                int dateIndex = cursor.getColumnIndex(Date);
                int timeIndex = cursor.getColumnIndex(Time);
                int staffNameIndex = cursor.getColumnIndex(StaffName);
                int serviceNameIndex = cursor.getColumnIndex(ServiceName);

                while (!cursor.isAfterLast()) {
                    if (appointmentIDIndex != -1 && staffIDIndex != -1 && dateIndex != -1 && timeIndex != -1 && staffNameIndex != -1 && serviceNameIndex != -1) {
                        int appointmentID = cursor.getInt(appointmentIDIndex);
                        int staffID = cursor.getInt(staffIDIndex);
                        String date = cursor.getString(dateIndex);
                        String time = cursor.getString(timeIndex);
                        String staffName = cursor.getString(staffNameIndex);
                        String serviceNameRetrieved = cursor.getString(serviceNameIndex);

                        bookO newAppointment = new bookO(appointmentID, clientID, staffID, date, time, staffName, serviceNameRetrieved);
                        returnList.add(newAppointment);
                    }
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
            db.close();
        }
        return returnList;
    }
}


