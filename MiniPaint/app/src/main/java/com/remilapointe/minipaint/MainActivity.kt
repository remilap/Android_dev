package com.remilapointe.minipaint

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View

import com.remilapointe.minipaint.util.BrushSizeDialog
import com.remilapointe.minipaint.util.ColorPickerDialog

class MainActivity : AppCompatActivity(), ColorPickerDialog.OnColorChangedListener, BrushSizeDialog.OnBrushSizeChangedListener {

    private var paintView: PaintView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paintView = findViewById(R.id.paintView)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView!!.init(metrics)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun colorChanged(color: Int) {
        paintView!!.setColor(color)
    }

    override fun brushSizeChanged(size: Int) {
        paintView!!.setBrushSize(size)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.men_color -> {
                ColorPickerDialog(this@MainActivity, this@MainActivity, paintView!!.mPaint.color).show()
                return true
            }
            R.id.men_brush_size -> {
                BrushSizeDialog(this@MainActivity, this@MainActivity, paintView!!.curBrushSize).show()
                return true
            }
            R.id.men_normal -> {
                paintView!!.normal()
                return true
            }
            R.id.men_emboss -> {
                paintView!!.emboss()
                return true
            }
            R.id.men_blur -> {
                paintView!!.blur()
                return true
            }
            R.id.men_clear -> {
                paintView!!.clear()
                return true
            }
            R.id.men_save -> {
                paintView!!.clear()
                return true
            }
            R.id.men_exit -> {
                paintView!!.clear()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
