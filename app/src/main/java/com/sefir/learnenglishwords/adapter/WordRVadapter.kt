package com.sefir.learnenglishwords.adapter


import android.app.Activity
import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sefir.learnenglishwords.R
import com.sefir.learnenglishwords.model.Word
import com.sefir.learnenglishwords.view.OwnWordList
import com.sefir.learnenglishwords.view.ShowOneOwnWord
import com.sefir.learnenglishwords.view.ShowWord
import kotlinx.android.synthetic.main.card_layout.view.*
import kotlinx.coroutines.launch

class WordRVadapter(val wordList: List<Word>) :
    RecyclerView.Adapter<WordRVadapter.CardViewObjectHolder>() {


    class CardViewObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewObjectHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return CardViewObjectHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewObjectHolder, position: Int) {

        holder.itemView.tv_word_name.text = wordList.get(position).wordName
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ShowOneOwnWord::class.java)
            intent.putExtra("type", wordList.get(position).uuid)
            it.context.startActivity(intent)
            (it.context as Activity).finish()
        }
    }

    override fun getItemCount(): Int {

        return wordList.size
    }

}