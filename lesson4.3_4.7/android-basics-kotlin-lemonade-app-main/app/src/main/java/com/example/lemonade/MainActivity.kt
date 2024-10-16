package com.example.lemonade

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    private val SELECT = "select"
    private val SQUEEZE = "squeeze"
    private val DRINK = "drink"
    private val RESTART = "restart"

    private var lemonadeState = SELECT
    private var lemonSize = -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, SELECT)
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()

        lemonImage?.setOnClickListener {
            clickLemonImage()
        }

        lemonImage?.setOnLongClickListener {
            showSnackbar()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    private fun clickLemonImage() {
        when (lemonadeState) {
            SELECT -> {
                lemonadeState = SQUEEZE
                val tree: LemonTree = lemonTree
                lemonSize = tree.pick()
                squeezeCount = 0
            }

            SQUEEZE -> {
                squeezeCount += 1
                lemonSize -= 1
                lemonadeState = if (lemonSize == 0) {
                    DRINK
                } else SQUEEZE
            }

            DRINK -> {
                lemonadeState = RESTART
                lemonSize = -1
            }

            RESTART -> lemonadeState = SELECT
        }

        setViewElements()
    }

    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)
        val lemonImage: ImageView = findViewById(R.id.image_lemon_state)

        when (lemonadeState) {
            SELECT -> {
                textAction.text = getString(R.string.lemon_select)
                lemonImage.setImageResource(R.drawable.lemon_tree)
            }
            SQUEEZE -> {
                textAction.text = getString(R.string.lemon_squeeze)
                lemonImage.setImageResource(R.drawable.lemon_squeeze)
            }
            DRINK -> {
                textAction.text = getString(R.string.lemon_drink)
                lemonImage.setImageResource(R.drawable.lemon_drink)
            }
            RESTART -> {
                textAction.text = getString(R.string.lemon_empty_glass)
                lemonImage.setImageResource(R.drawable.lemon_restart)
            }
        }
    }

    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
