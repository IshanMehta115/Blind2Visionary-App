package com.blind2visionary.andapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.blind2visionary.andapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_feedback, R.id.nav_contact, R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setUserInfo(navigationView);
    }

    private void setUserInfo(NavigationView view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            View headerView = view.getHeaderView(0);
            TextView nameText = headerView.findViewById(R.id.navigationUserName);
            TextView emailText = headerView.findViewById(R.id.navigationUserEmail);
            emailText.setText(email);
            nameText.setText(name);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))    {
            drawer.closeDrawer(GravityCompat.START);
        }
        else    {
            logoutPopup();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        if(id==R.id.action_developer) {
            intent = new Intent(this, DeveloperActivity.class);
//          intent.putExtra("key", value);
            startActivity(intent);
        }
        else if(id==R.id.action_logout) {
            logoutPopup();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutPopup()  {
        final AlertDialog.Builder logoutBuilder = new AlertDialog.Builder(this);
        logoutBuilder.setTitle("Logout");
        logoutBuilder.setMessage("Are you sure you want to logout?");

        logoutBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()  {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();//for logging out the user from the main screen
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        logoutBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogI, int which) {
                //close the dialogue
            }
        });
        logoutBuilder.create().show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}