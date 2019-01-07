package com.remilapointe.tictactoe.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.remilapointe.tictactoe.R

class GameEndDialog : DialogFragment() {

    private lateinit var rootView: View
    lateinit var activity: GameActivity
    lateinit var winnerName: String

    companion object {
        fun newInstance(activity: GameActivity, winnerName: String) : GameEndDialog {
            val dialog = GameEndDialog()
            dialog.activity = activity
            dialog.winnerName = winnerName
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViews()
        val alertDialog = AlertDialog.Builder(context)
            .setView(rootView)
            .setCancelable(false)
            .setPositiveButton(R.string.done) { _, _ -> onNewGame()}
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        return alertDialog
    }

    private fun initViews() {
        rootView = LayoutInflater.from(context)
            .inflate(R.layout.game_end_dialog, null, false)
        val tvWinner: TextView = rootView.findViewById(R.id.tv_winner)
        tvWinner.text = winnerName
    }

    private fun onNewGame() {
        dismiss()
        activity.promptForPlayers()
    }

}
