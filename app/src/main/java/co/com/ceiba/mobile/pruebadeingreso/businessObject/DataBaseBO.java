package co.com.ceiba.mobile.pruebadeingreso.businessObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.util.ArrayList;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.util.Const;
import co.com.ceiba.mobile.pruebadeingreso.util.Util;

public class DataBaseBO {
    public static boolean existDataBase() {
        File dbFile = new File(Util.dirApp(), Const.NAMEDATABASE);

        return dbFile.exists();
    }
    public static boolean createDataBase() {
        SQLiteDatabase db = null;
        try {
            File dbFile = new File(Util.dirApp(), Const.NAMEDATABASE);
            db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
            String tableUser = "CREATE TABLE IF NOT EXISTS User(id varchar(50), name varchar(50), phone varchar(20), email varchar(20))";
            String tablePost = "CREATE TABLE IF NOT EXISTS Post(id varchar(50), idUser varchar(50), title varchar(20), body varchar(20))";
            db.execSQL(tableUser);
            db.execSQL(tablePost);

            return true;
        } catch (Exception e) {

            return false;
        } finally {
            if (db != null)
                db.close();
        }
    }
    public static boolean saveUsers(ArrayList<User> users) {
        long rows = 0;
        SQLiteDatabase db = null;
        try {
            File dbFile = new File(Util.dirApp(), Const.NAMEDATABASE);
            if (existDataBase()) {
                db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
                ContentValues values = new ContentValues();
                for (User user : users){
                    if(existUser(user.getId()) == 0){
                        values.put("id", user.getId());
                        values.put("name", user.getName());
                        values.put("phone", user.getPhone());
                        values.put("email", user.getEmail());
                        rows = db.insertOrThrow("User", null, values);
                    }
                }

                return true;
            } else {

                return false;
            }
        } catch (Exception e) {

            return false;
        } finally {
            if (db != null)
                db.close();
        }
    }
    public static boolean savePosts(ArrayList<Post> posts) {
        long rows = 0;
        SQLiteDatabase db = null;
        try {
            File dbFile = new File(Util.dirApp(), Const.NAMEDATABASE);
            if (existDataBase()) {
                db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
                ContentValues values = new ContentValues();
                for (Post post : posts){
                    if(existPost(post) == 0){
                        values.put("id", post.getId());
                        values.put("idUser", post.getUserId());
                        values.put("title", post.getTitle());
                        values.put("body", post.getBody());
                        rows = db.insertOrThrow("Post", null, values);
                    }
                }

                return true;
            } else {

                return false;
            }
        } catch (Exception e) {

            return false;
        } finally {
            if (db != null)
                db.close();
        }
    }
    public static int existPost(Post post) {
        String mensaje = "";
        int total = 0;
        SQLiteDatabase db = null;
        try {
            File dbFile = new File(Util.dirApp(), Const.NAMEDATABASE);
            if (existDataBase()) {
                db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
                String query = "SELECT count(*) AS total " +
                        "FROM (SELECT * FROM Post WHERE id = '" + post.getId() + " AND idUser = '" + post.getUserId() + "') ";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    total = cursor.getInt(cursor.getColumnIndex("total"));
                }
                cursor.close();
            } else {
                mensaje = "No existe la base de datos.";
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        } finally {
            closeDataBase(db);
        }

        return total;
    }
    public static int existUser(String idUser) {
        String mensaje = "";
        int total = 0;
        SQLiteDatabase db = null;
        try {
            File dbFile = new File(Util.dirApp(), Const.NAMEDATABASE);
            if (existDataBase()) {
                db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
                String query = "SELECT count(*) AS total FROM (SELECT * FROM User WHERE id = '" + idUser + "') ";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    total = cursor.getInt(cursor.getColumnIndex("total"));
                }
                cursor.close();
            } else {
                mensaje = "No existe la base de datos.";
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        } finally {
            closeDataBase(db);
        }

        return total;
    }
    public static ArrayList<User> getListUserDb(String search) {
        String mensaje = "";
        User user;
        SQLiteDatabase db = null;
        ArrayList<User> listUser = new ArrayList<>();
        try {
            File dbFile = new File(Util.dirApp(), Const.NAMEDATABASE);
            if (existDataBase()) {
                db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
                String query = "SELECT * FROM User where name LIKE '" + search + "%' ";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        user = new User();
                        user.setId(cursor.getString(cursor.getColumnIndex("id")));
                        user.setName(cursor.getString(cursor.getColumnIndex("name")));
                        user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                        user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        listUser.add(user);
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                mensaje = "No existe la base de datos.";
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        } finally {
            closeDataBase(db);
        }

        return listUser;
    }
    public static ArrayList<Post> getPostUser(String idUser) {
        String mensaje = "";
        Post post;
        SQLiteDatabase db = null;
        ArrayList<Post> listPost = new ArrayList<>();
        try {
            File dbFile = new File(Util.dirApp(), Const.NAMEDATABASE);
            if (existDataBase()) {
                db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
                String query = "SELECT * FROM Post where idUser = '" + idUser + "' ";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        post = new Post();
                        post.setId(cursor.getString(cursor.getColumnIndex("id")));
                        post.setUserId(cursor.getString(cursor.getColumnIndex("idUser")));
                        post.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        post.setBody(cursor.getString(cursor.getColumnIndex("body")));
                        listPost.add(post);
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                mensaje = "No existe la base de datos.";
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        } finally {
            closeDataBase(db);
        }

        return listPost;
    }
    public static void closeDataBase(SQLiteDatabase db) {
        if (db != null) {
            if (db.inTransaction()) db.endTransaction();
            db.close();
        }
    }
}
