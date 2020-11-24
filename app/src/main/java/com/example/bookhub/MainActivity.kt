package com.example.bookhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bookhub.Fragment.AboutApp
import com.example.bookhub.Fragment.DashboardFragment
import com.example.bookhub.Fragment.FavouritesFragment
import com.example.bookhub.Fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var CoordinatorLayout: CoordinatorLayout;
    lateinit var  drawerLayout:DrawerLayout;
    lateinit var  navigationView: NavigationView
    lateinit var  toolbar: androidx.appcompat.widget.Toolbar
    lateinit var  framelayout:FrameLayout
    var previouesMenueItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.beginTransaction()
//            .replace(R.id.framelayout,DashboardFragment())
//            .addToBackStack("Dashboard")
//            .commit()
//
//       // drawerLayout.closeDrawers()
//        supportActionBar?.title="Dashboard"




        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navdrawer);
         toolbar = findViewById(R.id.toolbarLayout);
        framelayout=findViewById(R.id.framelayout)
        CoordinatorLayout=findViewById(R.id.coordinatorLayout);

        setSupportActionBar()


        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,R.string.open_drawer,R.string.close_drawer);


            drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState()




        openDashBoard()
        navigationView.setNavigationItemSelectedListener {


            if(previouesMenueItem != null){
                previouesMenueItem?.isChecked=false

            }
            it.isCheckable=true
            it.isChecked=true
            previouesMenueItem=it



            when(it.itemId){

                R.id.dashboard ->{

                    openDashBoard()
                    drawerLayout.closeDrawers()
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.framelayout,DashboardFragment())
//                        .commit()
//                    drawerLayout.closeDrawers()
//                    supportActionBar?.title="Dashboard"

                    //Toast.makeText(this@MainActivity,"clicked on Dashboard",Toast.LENGTH_SHORT).show()
                }
                R.id.favorites ->{

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,FavouritesFragment())
                        .addToBackStack("favourites")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Favourites"
                    // Toast.makeText(this@MainActivity,"clicked on favoraties",Toast.LENGTH_SHORT).show()
                }
              /*  R.id.profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,ProfileFragment())
                        .addToBackStack("Profile")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Profile"

                    //Toast.makeText(this@MainActivity,"clicked on profile",Toast.LENGTH_SHORT).show()

                }*/
                R.id.about_app ->{

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,AboutApp())
                        .addToBackStack("AboutApp")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="AboutApp"
//                    Toast.makeText(this@MainActivity,"clicked on aboutApp",Toast.LENGTH_SHORT).show()

                }


                R.id.logout ->{



                   // drawerLayout.closeDrawers()
                    this.finish();

//                    Toast.makeText(this@MainActivity,"clicked on aboutApp",Toast.LENGTH_SHORT).show()

                }


            }


            return@setNavigationItemSelectedListener true
        }

    }



    fun setSupportActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar Title"
         supportActionBar?.setHomeButtonEnabled(true)
         supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val  id= item?.itemId
        if(id== android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }



        return super.onOptionsItemSelected(item)
    }

      fun openDashBoard(){
          val fragment= DashboardFragment()
          val transaction=supportFragmentManager.beginTransaction()

          transaction.replace(R.id.framelayout,fragment)
          transaction.commit()
          supportActionBar?.title="Dashboard"
          navigationView.setCheckedItem(R.id.dashboard)
              //  navigationView.setCheckedItem(R.id.dashboard)
            }




    override fun onBackPressed() {
        //super.onBackPressed()

        val frg =supportFragmentManager.findFragmentById(R.id.framelayout)

        when(frg){
            !is DashboardFragment -> openDashBoard()
            else -> super.onBackPressed()
        }

    }
}
