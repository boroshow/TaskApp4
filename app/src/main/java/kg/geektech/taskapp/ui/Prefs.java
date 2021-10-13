package kg.geektech.taskapp.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    SharedPreferences sharedPreferences;

    public Prefs(Context context) {
        sharedPreferences =
                context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }
    public void saveBoardState(){
        sharedPreferences.edit().putBoolean("isBoardShown",true).apply();
    }
}
