package com.allstars.tictactoe

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allstars.tictactoe.databinding.ActivityMainBinding
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var currentTurn = Turn.Cross
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.gamegrid.adapter = TTTAdapater {
            val view = it as TextView
            if (view.text.isBlank()) {
                view.text = currentTurn.symbol
                setCurrentTurn()
            }
             showResultIfMatchEnded()




        }

        binding.gamegrid.layoutManager = GridLayoutManager(this,3)

    }
    fun setCurrentTurn(){
        when(currentTurn){
            Turn.Cross->{
             currentTurn = Turn.Nought
                binding.titleTv.text = "Turn ${currentTurn.symbol}"
            }
            Turn.Nought->{
                currentTurn = Turn.Cross
                binding.titleTv.text = "Turn ${currentTurn.symbol}"
            }
        }
    }
    fun resetBoard(){
        binding.gamegrid.children.forEach {
            (it as TextView).text = ""
        }
    }
    fun checkResult( turn : Turn) : Boolean{
        //Check for Horizontal Wins

          val listofHorizontalResults = listOf(
              binding.gamegrid.children.filter {

             binding.gamegrid.getChildAdapterPosition(it) in  setOf(0,1,2)
              }.all {
             (it as TextView).text == turn.symbol },
              binding.gamegrid.children.filter {
                  binding.gamegrid.getChildAdapterPosition(it) in  setOf(3,4,5)
              }.all {
                  (it as TextView).text == turn.symbol },
              binding.gamegrid.children.filter {
                  binding.gamegrid.getChildAdapterPosition(it) in  setOf(6,7,8)
              }.all {
                  (it as TextView).text == turn.symbol }
          ).any { it }
        val listOfVerticalResults = listOf(
            binding.gamegrid.children.filter {
                binding.gamegrid.getChildAdapterPosition(it) in  setOf(0,3,6)
            }.all {
                (it as TextView).text == turn.symbol },
            binding.gamegrid.children.filter {
                binding.gamegrid.getChildAdapterPosition(it) in  setOf(1,4,7)
            }.all {
                (it as TextView).text == turn.symbol },
            binding.gamegrid.children.filter {
                binding.gamegrid.getChildAdapterPosition(it) in  setOf(2,5,8)
            }.all {
                (it as TextView).text == turn.symbol }
        ).any { it  }

        val listofDiagonalResults = listOf(
            binding.gamegrid.children.filter {
                binding.gamegrid.getChildAdapterPosition(it) in  setOf(0,4,8)
            }.all {
                (it as TextView).text == turn.symbol },
            binding.gamegrid.children.filter {
                binding.gamegrid.getChildAdapterPosition(it) in  setOf(2,4,6)
            }.all {
                (it as TextView).text == turn.symbol }

        ).any { it   }
        val result = listOf(listofDiagonalResults,listofHorizontalResults,listOfVerticalResults).any{ it }
       return   result
    }
    fun showResultIfMatchEnded(){

        val endOfGamePopup = AlertDialog.Builder(this@MainActivity)
            .setCancelable(false)
            .setPositiveButton("Reset Board"){ _,_->resetBoard()}
            .create()
        if (checkResult(Turn.Nought)){
            endOfGamePopup.setTitle("Nought Won")
            endOfGamePopup.show()
        }else if(checkResult(Turn.Cross)){
            endOfGamePopup.setTitle("Cross Won")
            endOfGamePopup.show()
        }else if(binding.gamegrid.children.all {
                (it as TextView).text.isNotBlank()
            }){
            endOfGamePopup.setTitle("Draw")
            endOfGamePopup.show()
        }
    }



}