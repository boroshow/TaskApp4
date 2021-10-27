package kg.geektech.taskapp35.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {

    private final SharedPreferences sharedPreferences;

    public Prefs(Context context) {
        sharedPreferences =
                context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }
    public void saveBoardState(){
        sharedPreferences.edit().putBoolean("isBoardShown",true).apply();
    }
    public boolean isBoardShown(){
        return sharedPreferences.getBoolean("isBoardShown",false);
    }

    public void saveAvatar(Uri image){
        sharedPreferences.edit().putString("image",image.toString()).apply();
    }
    public String getAvatar(){
        return sharedPreferences.getString("image"," ");
    }
    public void saveEditText(String name){
        sharedPreferences.edit().putString("name",name).apply();
    }
    public String getTextEdit(){
        return sharedPreferences.getString("name"," ");
    }



}
