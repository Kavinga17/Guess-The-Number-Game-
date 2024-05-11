package com.example.t2kao.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.t2kao.R
import com.example.t2kao.models.Result

class ResultAdapter(context: Context, resultList: List<Result>) :
    ArrayAdapter<Result>(context, 0, resultList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_result, parent, false)
            viewHolder = ViewHolder(
                view.findViewById(R.id.tvComputerGuess),
                view.findViewById(R.id.tvPlayerGuess),
                view.findViewById(R.id.imgResult)
            )
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val result = getItem(position)
        result?.let {
            viewHolder.tvComputerGuess.text = "Computer Guess: ${it.computerGuess}"
            viewHolder.tvPlayerGuess.text = "Your Guess: ${it.playerGuess}"

            if (it.playerWins) {
                viewHolder.ivResult.setImageResource(R.drawable.win)
            } else {
                viewHolder.ivResult.setImageResource(R.drawable.loss)
            }
        }

        return view!!
    }

    private class ViewHolder(
        val tvComputerGuess: TextView,
        val tvPlayerGuess: TextView,
        val ivResult: ImageView
    )
}
