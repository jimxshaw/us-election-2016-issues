package me.jimmyshaw.politicalpositions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class IssueListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String EXTRA_CANDIDATE_NAME = "candidate_name";

    private DrawerLayout mDrawerLayout;

    private String mCandidateName;

    private Fragment mFragmentOriginal;
    private Fragment mFragmentNew;
    private FragmentManager mFragmentManager;

    public static Intent newIntent(Context packageContext, String candidateName) {
        Intent intent = new Intent(packageContext, IssueListActivity.class);
        intent.putExtra(EXTRA_CANDIDATE_NAME, candidateName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(drawerToggle);
        // If the syncState method is not called then the drawer toggle icon AKA "Hamburger icon"
        // either won't synchronize with the drawer layout or the icon won't appear at all.
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Remove the gray color tint from nav menu item icons. Otherwise, the default gray
        // color would cover the entire icon image.
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentOriginal = mFragmentManager.findFragmentById(R.id.fragment_content_container);

        if (mFragmentOriginal == null) {

            if (getIntent().getStringExtra(EXTRA_CANDIDATE_NAME) != null) {
                mCandidateName = getIntent().getStringExtra(EXTRA_CANDIDATE_NAME);
            }
            else {
                mCandidateName = "none";
            }

            mFragmentOriginal = IssueListFragment.newInstance(mCandidateName);
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_content_container, mFragmentOriginal)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.issue_list, menu);
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
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        switch(menuItem.getItemId()) {
            case R.id.nav_drawer_menu_item_clinton:
                filterIssueListByCandidate(getResources().getString(R.string.candidate_clinton));
                break;
            case R.id.nav_drawer_menu_item_sanders:
                filterIssueListByCandidate(getResources().getString(R.string.candidate_sanders));
                break;
            case R.id.nav_drawer_menu_item_trump:
                filterIssueListByCandidate(getResources().getString(R.string.candidate_trump));
                break;
            case R.id.nav_drawer_menu_item_cruz:
                filterIssueListByCandidate(getResources().getString(R.string.candidate_cruz));
                break;
            default:
                filterIssueListByCandidate(getResources().getString(R.string.candidate_filter_none));
                break;
        }
        return true;
    }

    // Filter issue list by candidate.
    private void filterIssueListByCandidate(String candidateName) {
        hideDrawer();
        mFragmentNew = IssueListFragment.newInstance(candidateName);
        mFragmentManager.beginTransaction()
                .hide(mFragmentOriginal)
                .add(R.id.fragment_content_container, mFragmentNew)
                .commit();
    }

    // Show the navigation drawer.
    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    // Hide the navigation drawer.
    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    // When the user pushes the back button to close the app, if the drawer is open then hide the
    // drawer. Otherwise, perform the usual action with the back button press which is closing
    // the app.
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            hideDrawer();
        }
        else {
            super.onBackPressed();
        }
    }
}
