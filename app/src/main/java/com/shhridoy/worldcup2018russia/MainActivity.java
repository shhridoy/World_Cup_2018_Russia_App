package com.shhridoy.worldcup2018russia;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.myDataBase.DatabaseHelper;
import com.shhridoy.worldcup2018russia.myPagerClasses.PagerAdapter;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.GoalsListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.MatchesListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.TablesListItems;
import com.shhridoy.worldcup2018russia.myRetrofitApi.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    boolean isLinkFailed = false;
    DatabaseHelper dbHelper;
    static boolean noDataInMatches;
    static boolean noDataInTables;
    static boolean noDataInGoals;

    Retrofit retrofit;
    Api api;
    Call<List<MatchesListItems>> callMatches;
    Call<List<TablesListItems>> callTables;
    Call<List<GoalsListItems>> callGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fab.hide();
            }
        });

         //dbHelper = new DatabaseHelper(this);
         //noDataInMatches = dbHelper.retrieveMatchesData().getCount() == 0;
         //noDataInTables = dbHelper.retrievePointsData().getCount() == 0;
         //noDataInGoals = dbHelper.retrieveGoalsData().getCount() == 0;

         /*if (isInternetOn()) {
             serverChangeFunc();
         } else {
             Snackbar.make(findViewById(R.id.COR), "Turn your internet connection on!!", Snackbar.LENGTH_LONG)
                     .setAction("Action", null).show();
         }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Matches"));
        tabLayout.addTab(tabLayout.newTab().setText("Tables"));
        tabLayout.addTab(tabLayout.newTab().setText("Goals"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void serverChangeFunc() {
        if (isLinkFailed) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(Api.class);

            callMatches = api.getMatches2();
            callTables = api.getTables2();
            callGoals = api.getGoals2();
            retrieveJsonData();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(Api.class);

            callMatches = api.getMatches1();
            callTables = api.getTables1();
            callGoals = api.getGoals1();
            retrieveJsonData();
        }
    }

    // FUNCTION FOR RETRIEVE THE JSON DATA USING RETROFIT
    private void retrieveJsonData() {

        callMatches.enqueue(new Callback<List<MatchesListItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<MatchesListItems>> call, @NonNull retrofit2.Response<List<MatchesListItems>> response) {

                List<MatchesListItems> matches = response.body();

                if (matches != null) {
                    int i = 0;
                    for (MatchesListItems mat : matches) {
                        if (noDataInMatches) {
                            saveMatchesData(mat.getDate(), mat.getRound(), mat.getTeam1(), mat.getTeam2(), mat.getScore());
                        } else {
                            updateMatchesData(i+1, mat.getDate(), mat.getRound(), mat.getTeam1(), mat.getTeam2(), mat.getScore());
                        }
                        i++;
                    }
                }

                noDataInMatches = dbHelper.retrieveMatchesData().getCount() == 0;
                noDataInTables = dbHelper.retrievePointsData().getCount() == 0;
                noDataInGoals = dbHelper.retrieveGoalsData().getCount() == 0;
            }

            @Override
            public void onFailure(@NonNull Call<List<MatchesListItems>> call, @NonNull Throwable t) {
                Log.i("ERROR", t.getMessage());
                Toast.makeText(getApplicationContext(), "Server access failed!", Toast.LENGTH_SHORT).show();
                isLinkFailed = true;
                serverChangeFunc();
            }
        });

        callTables.enqueue(new Callback<List<TablesListItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<TablesListItems>> call, @NonNull retrofit2.Response<List<TablesListItems>> response) {

                List<TablesListItems> tables = response.body();

                if (tables != null) {
                    int i = 0;
                    for (TablesListItems tab : tables) {
                        if (noDataInTables) {
                            saveTablesData(tab.getGroup(), tab.getTeamNo(), tab.getTeamName(), tab.getStatus());
                        } else {
                            updatesTablesData(i+1, tab.getGroup(), tab.getTeamNo(), tab.getTeamName(), tab.getStatus());
                        }
                        i++;
                    }
                }

                noDataInMatches = dbHelper.retrieveMatchesData().getCount() == 0;
                noDataInTables = dbHelper.retrievePointsData().getCount() == 0;
                noDataInGoals = dbHelper.retrieveGoalsData().getCount() == 0;
            }

            @Override
            public void onFailure(@NonNull Call<List<TablesListItems>> call, @NonNull Throwable t) {
                Log.i("ERROR", t.getMessage());
                Toast.makeText(getApplicationContext(), "Server access failed!", Toast.LENGTH_SHORT).show();
                isLinkFailed = true;
                serverChangeFunc();
            }
        });

        callGoals.enqueue(new Callback<List<GoalsListItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<GoalsListItems>> call, @NonNull retrofit2.Response<List<GoalsListItems>> response) {

                List<GoalsListItems> goals = response.body();

                if (goals != null) {
                    int i = 0;
                    for (GoalsListItems gol : goals) {
                        if (noDataInGoals) {
                            saveGoalsData(gol.getName(), gol.getGoal(), gol.getTag());
                        } else {
                            updateGoalsData(i+1, gol.getName(), gol.getGoal(), gol.getTag());
                        }
                        i++;
                    }
                }

                noDataInMatches = dbHelper.retrieveMatchesData().getCount() == 0;
                noDataInTables = dbHelper.retrievePointsData().getCount() == 0;
                noDataInGoals = dbHelper.retrieveGoalsData().getCount() == 0;
            }

            @Override
            public void onFailure(@NonNull Call<List<GoalsListItems>> call, @NonNull Throwable t) {
                Log.i("ERROR", t.getMessage());
                Toast.makeText(getApplicationContext(), "Server access failed!", Toast.LENGTH_SHORT).show();
                isLinkFailed = true;
                serverChangeFunc();
            }
        });

    }

    private void saveMatchesData (String date, String round, String team1, String team2, String score) {
        boolean added = dbHelper.insertMatchesData(date, round, team1, team2, score);
        if (!added) {
            Toast.makeText(this, "Data can't be added!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMatchesData(int id, String date, String round, String team1, String team2, String score) {
        boolean updated = dbHelper.updateMatchesData(id, date, round, team1, team2, score);
        if (!updated) {
            Toast.makeText(this, "Doesn't updated!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveTablesData (String group, String teamNo, String teamName, String status) {
        boolean added = dbHelper.insertPointsData(group, teamNo, teamName, status);
        if (!added) {
            Toast.makeText(this, "Data can't be added!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatesTablesData(int id, String group, String teamNo, String teamName, String status) {
        boolean updated = dbHelper.updatePointsData(id, group, teamNo, teamName, status);
        if (!updated) {
            Toast.makeText(this, "Doesn't updated!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveGoalsData(String name, String goal, String tag) {
        boolean added = dbHelper.insertGoalsData(name, goal, tag);
        if (!added) {
            Toast.makeText(this, "Data can't be added!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateGoalsData (int id, String name, String goal, String tag) {
        boolean updated = dbHelper.updateGoalsData(id, name, goal, tag);
        if (!updated) {
            Toast.makeText(this, "Doesn't updated!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        assert connec != null;
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;
        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            return false;
        }

        return false;
    }
}
