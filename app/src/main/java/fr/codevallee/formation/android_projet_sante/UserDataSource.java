package fr.codevallee.formation.android_projet_sante;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tgoudouneix on 19/10/2017.
 */

public class UserDataSource {
    private final UserDBHelper helper;
    private SQLiteDatabase db;

    public UserDataSource(Context context) {
        helper = new UserDBHelper(context);
    }

    public SQLiteDatabase getDb() {
        if (db == null) {
            open();
        }
        return db;
    }

    public UserDBHelper getHelper() {
        return helper;
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public UserDAO newUserDAO() {
        return new UserDAO(this);
    }
}
