package com.example.gmail_layout

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var lv: ListView

    private val gmailTitle = listOf("Edurila.com", "Chris Abad", "Tuto.com", "support", "Matt from lonic")

    private val gmailTime = listOf("12:34 PM", "11:22 AM", "11:04 AM", "10:26 AM", "9:30 AM")

    private val gmailText = listOf("$19 Only (Frist 10 spots) - Destselling...\nAre you looking to Learn Web Designin...",
        "Help make Campaign Monitor better\nLet us know your thoughts! No Images...",
        "8h de formation gratuite et les nouvea...\nPhotoshop, SEO, Blender, CSS, Wordpre...",
        "Société Ovh: suivi de vos services - hp...\nSAS OVH - http://www.ovh.com 2 rue K...",
        "The New lonic Creator Is Here!\nAnnouncing the all-new Creator, build...")

    private val myList: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.lv)

        val random = Random()
        for (i in gmailTitle.indices) {
            val avatar = gmailTitle[i].first().uppercase()
            val colorAvatar = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setSize(50, 50)
                setColor(colorAvatar)
            }
            myList.add(User(avatar, gmailTitle[i], gmailTime[i],gmailText[i], isStarred = false, drawable))
        }

        val adapter = GmailAdapter(this, R.layout.gmail_item, myList)
        lv.adapter = adapter

    }
}