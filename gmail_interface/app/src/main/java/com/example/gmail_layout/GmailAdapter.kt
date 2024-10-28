package com.example.gmail_layout

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class GmailAdapter(
    private val context: Activity,
    private val layoutId: Int,
    private val myList: ArrayList<User>
): ArrayAdapter<User>(context, layoutId, myList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutId, parent, false)

        val user = myList[position]

        val avatarView = view.findViewById<TextView>(R.id.gmImage)
        val mailView = view.findViewById<TextView>(R.id.gmTitle)
        val timeView = view.findViewById<TextView>(R.id.gmTime)
        val textView = view.findViewById<TextView>(R.id.gmText)
        val starView = view.findViewById<ImageView>(R.id.gmStar)

        avatarView.text = user.avatar
        avatarView.background = user.avatarDrawable
        mailView.text = user.mail
        timeView.text = user.time
        textView.text = user.text
        val starIcon = if (user.isStarred) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
        starView.setImageResource(starIcon)

        starView.setOnClickListener {
            user.isStarred = !user.isStarred
            notifyDataSetChanged()
        }

        return view
    }
}