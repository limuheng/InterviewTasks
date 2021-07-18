package com.muheng.trevitask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.*
import androidx.core.widget.doOnTextChanged

const val MSG_RANDOM_CELL = 1234567
const val DURATION_PERIOD = 10000L

class MainActivity : AppCompatActivity(), Handler.Callback {

    private var editCol: EditText? = null
    private var editRow: EditText? = null
    private var drawBtn: Button? = null

    private var gridView: GridView? = null

    private val mainPresent: IMainPresenter = MainPresenterImpl(this)

    private val handler: Handler = Handler(this)

    private val randomTask = Runnable {
        mainPresent.selectPoint(mainPresent.genRandomPoint())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCol = findViewById(R.id.input_col)
        editRow = findViewById(R.id.input_row)
        drawBtn = findViewById(R.id.drow)

        gridView = findViewById(R.id.gridview)

        editCol?.doOnTextChanged { text, _, _, _ ->
            try {
                mainPresent.setCol(text.toString().toInt())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        editRow?.doOnTextChanged { text, _, _, _ ->
            try {
                mainPresent.setRow(text.toString().toInt())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        drawBtn?.setOnClickListener {
            mainPresent.createAdapter()?.let {
                gridView?.numColumns = mainPresent.getCol()
                gridView?.adapter = it

                handler.sendMessage(handler.obtainMessage(MSG_RANDOM_CELL))
            } ?: run {
                Toast.makeText(this, "Unable to create adapter for GridView", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    override fun handleMessage(msg: Message?): Boolean {
        return when (msg?.what) {
            MSG_RANDOM_CELL -> {
                randomTask.run()
                val msg = handler.obtainMessage(MSG_RANDOM_CELL)
                handler.sendMessageDelayed(msg, DURATION_PERIOD)
                true
            }
            else -> {
                false
            }
        }
    }

}
