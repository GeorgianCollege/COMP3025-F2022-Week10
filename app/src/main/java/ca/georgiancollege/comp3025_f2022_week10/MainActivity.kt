package ca.georgiancollege.comp3025_f2022_week10

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var FavouriteTVShows = mutableListOf<TVShow>(
        TVShow("House of the Dragon", "HBO"),
        TVShow("Lord of the Rings", "Prime Video"),
        TVShow("Andor", "Disney"),
        TVShow("Severance", "AppleTv"),
        TVShow("Star Trek: Strange New Worlds", "Paramount+"))

    lateinit var addTVShowFAB: FloatingActionButton
    lateinit var firstAdapter: FirstAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstAdapter = FirstAdapter(FavouriteTVShows)
        val recyclerView: RecyclerView = findViewById(R.id.First_Recycler_View)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = firstAdapter

        addTVShowFAB = findViewById(R.id.add_TV_Show_FAB)

        addTVShowFAB.setOnClickListener{
            showCreateTVShowDialog()
        }
    }

    private fun showCreateTVShowDialog() {
        val dialogTitle = getString(R.string.dialog_title)
        val positiveButtonTitle = getString(R.string.add_tv_show)
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.add_new_tv_show_item, null)

        builder.setTitle(dialogTitle)
        builder.setView(view)

        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            dialog.dismiss()
            val tvShowTitleEditText = view.findViewById<EditText>(R.id.TV_Show_Title_EditText)
            val studioTitleEditText = view.findViewById<EditText>(R.id.Studio_Name_EditText)
            val newTVShow = TVShow(tvShowTitleEditText.text.toString(), studioTitleEditText.text.toString())
            FavouriteTVShows.add(newTVShow)
            firstAdapter.notifyItemInserted(FavouriteTVShows.size)
        }
        builder.create().show()
    }

}