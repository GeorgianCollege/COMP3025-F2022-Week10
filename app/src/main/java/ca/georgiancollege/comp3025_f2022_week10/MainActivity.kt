package ca.georgiancollege.comp3025_f2022_week10

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var TVShows: MutableList<TVShow>

    lateinit var addTVShowFAB: FloatingActionButton
    lateinit var tvShowAdapter: TVShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialization
        database = Firebase.database.reference
        TVShows = mutableListOf<TVShow>() // creates an empty List container
        tvShowAdapter = TVShowAdapter(TVShows)

        initializeRecyclerView()
        initializeFAB()
        addTVShowEventListener(database)
    }

    private fun initializeFAB() {
        addTVShowFAB = findViewById(R.id.add_TV_Show_FAB)
        addTVShowFAB.setOnClickListener {
            showCreateTVShowDialog()
        }
    }

    private fun initializeRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.First_Recycler_View)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = tvShowAdapter
    }

    fun writeNewTVShow(tvShow: TVShow)
    {
        var id = TVShows.size.toString()
        database.child("TVShows").child(id).setValue(tvShow)
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
            writeNewTVShow(newTVShow)

            /*
            TVShows.add(newTVShow)
            tvShowAdapter.notifyItemInserted(TVShows.size)
             */
        }
        builder.create().show()
    }

    private fun addTVShowEventListener(dbReference: DatabaseReference)
    {
        val TVShowListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                TVShows.clear()
                val tvShowDB = dataSnapshot.child("TVShows").children

                for(tvShow in tvShowDB)
                {
                    var newShow = tvShow.getValue(TVShow::class.java)

                    if(newShow != null)
                    {
                        TVShows.add(newShow)
                        tvShowAdapter.notifyDataSetChanged()
                    }
                }

                for(tvShow in TVShows)
                {
                    Log.i("show", "tvShow: $tvShow")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("tvShowError", "loadTVShow:cancelled", databaseError.toException())
            }
        }
        dbReference.addValueEventListener(TVShowListener)
    }

}