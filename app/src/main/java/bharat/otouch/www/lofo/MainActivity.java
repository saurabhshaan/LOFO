package bharat.otouch.www.lofo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    android.support.v7.app.ActionBar actionBar;

    private DrawerLayout mdraer;
    private ActionBarDrawerToggle mtoggle;
    ImageButton bt1,bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mdraer = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBar = getSupportActionBar();
        actionBar.show();

        mtoggle = new ActionBarDrawerToggle(this, mdraer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mdraer.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        actionBar.setDisplayHomeAsUpEnabled(true);

        bt1=(ImageButton) findViewById(R.id.ib1);
        bt2=(ImageButton) findViewById(R.id.ib2);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ViewLostProduct.class);
                startActivity(intent);
                Log.d("TAG","after click");
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PostItem.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent h=new Intent(this, MainActivity.class);
            startActivity(h);
            finish();
        } else if (id == R.id.nav_about) {
            Intent a=new Intent(this,AboutUs.class);
            startActivity(a);
        } else if (id == R.id.nav_contact_us) {
            Intent c=new Intent(this,ContactUs.class);
            startActivity(c);
        }
        else if (id == R.id.nav_feed) {
            Intent f=new Intent(this,Feedback.class);
            startActivity(f);
        }else if(id==R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now Learn Android with AndroidSolved clicke here to visit https://androidsolved.wordpress.com/ ");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        return false;
    }

}


