package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import utils.Utils;

public class MenuActivity extends AppCompatActivity {

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    private boolean isUserFirstTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MenuActivity.this, PREF_USER_FIRST_TIME, "true"));
        Intent introIntent = new Intent(MenuActivity.this, ConnectBluetooth.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);


        // TODO: don't forget to uncomment before release!
        //if (isUserFirstTime)
            startActivity(introIntent);

    }




}
