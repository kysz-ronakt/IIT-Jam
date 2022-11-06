package Sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.User;
import lombok.Data;

@Data
public class DatabaseHelper extends SQLiteOpenHelper {

        private static final int databaseVersion = 1;
        private static final String databaseName = "IIT-Jam.db";

        private static final String tableStudent = "studentInfo";

        private static final String ColumnStudentID = "id";
        private static final String ColumnStudentFname ="firstName";
        private static final String ColumnStudentLname = "lastName";
        private static final String ColumnStudentEmail = "email";
        private static final String ColumnStudentPassword = "password";


    // create table sql query
    private String createTableStudent = "CREATE TABLE " + tableStudent +
            "(" + ColumnStudentID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ColumnStudentFname + " TEXT,"
                + ColumnStudentLname + " TEXT,"
                + ColumnStudentEmail + " TEXT,"
                + ColumnStudentPassword + " TEXT" + ")";
    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + tableStudent;


    //Constructor to initialize all the values of the database
    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableStudent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //Drop User Table if exist
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ColumnStudentFname, user.getFirstName());
        values.put(ColumnStudentLname, user.getLastName());
        values.put(ColumnStudentEmail, user.getEmail());
        values.put(ColumnStudentPassword, user.getPassword());
        // Inserting Row
        db.insert(tableStudent, null, values);
        db.close();
    }


    //this method is to fetch all the user data

    @SuppressLint("Range")
    public List<User> getAllUsers(){

        //array of column to fetch
        String[] columns = {
                ColumnStudentID,
                ColumnStudentFname,
                ColumnStudentLname,
                ColumnStudentEmail,
                ColumnStudentPassword

        };
        //sorting orders
        String sortOrder  = ColumnStudentFname + "ASC";

        List<User> users = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = sqLiteDatabase.query(tableStudent, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ColumnStudentID))));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(ColumnStudentFname)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(ColumnStudentLname)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(ColumnStudentEmail)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(ColumnStudentPassword)));
                // Adding user record to list
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        // return user list
        return users;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ColumnStudentFname, user.getFirstName());
        values.put(ColumnStudentLname, user.getLastName());
        values.put(ColumnStudentEmail, user.getEmail());
        values.put(ColumnStudentPassword, user.getPassword());
        // updating row
        db.update(tableStudent, values, ColumnStudentID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();

    }

    //delete the user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(tableStudent, ColumnStudentID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                ColumnStudentID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = ColumnStudentEmail + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(tableStudent, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                ColumnStudentID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = ColumnStudentEmail + " = ?" + " AND " + ColumnStudentPassword + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(tableStudent, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}
