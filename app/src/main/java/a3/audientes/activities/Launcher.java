package a3.audientes.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import a3.audientes.R;
import a3.audientes.fragments.Dialog;
import a3.audientes.fragments.SplashScreen;

public final class Launcher extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        setStatusBarTrans();

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);

        //showEditDialog();

        if (savedInstanceState == null) {
            Fragment splashScreen = new SplashScreen();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.emptyFrame, splashScreen)
                    .commit();
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO HALP IT WONT BE TRANSPARTEN
    public void setStatusBarTrans(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        Dialog editNameDialogFragment = Dialog.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }
}
