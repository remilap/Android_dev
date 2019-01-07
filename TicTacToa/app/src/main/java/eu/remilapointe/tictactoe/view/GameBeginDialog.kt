package eu.remilapointe.tictactoe.view

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import eu.remilapointe.tictactoe.R

class GameBeginDialog : DialogFragment() {

    private lateinit var layoutPlayer1: TextInputLayout
    private lateinit var layoutPlayer2: TextInputLayout

    private lateinit var etPlayer1: TextInputEditText
    private lateinit var etPlayer2: TextInputEditText

    lateinit var player1: String
    lateinit var player2: String

    private lateinit var rootView: View
    lateinit var activity: GameActivity

    companion object {
        fun newInstance(activity: GameActivity) : GameBeginDialog {
            val dialog = GameBeginDialog()
            dialog.activity = activity
            dialog.player1 = ""
            dialog.player2 = ""
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViews()
        val alertDialog = AlertDialog.Builder(context!!)
            .setView(rootView)
            .setTitle(R.string.game_dialog_title)
            .setCancelable(false)
            .setPositiveButton(R.string.done, null)
            .setNegativeButton(R.string.quit, null)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.setOnShowListener {
            onDialogShow(alertDialog)
        }
        return alertDialog
    }

    private fun initViews() {
        rootView = LayoutInflater.from(context)
            .inflate(R.layout.game_begin_dialog, null, false)

        layoutPlayer1 = rootView.findViewById(R.id.layout_player1)
        layoutPlayer2 = rootView.findViewById(R.id.layout_player2)
        layoutPlayer1.isErrorEnabled = false
        layoutPlayer2.isErrorEnabled = false
        etPlayer1 = rootView.findViewById(R.id.et_player1)
        etPlayer2 = rootView.findViewById(R.id.et_player2)
        addTextWatchers()
    }

    private fun onDialogShow(dialog: AlertDialog) {
        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            onDoneClicked()
        }
        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        negativeButton.setOnClickListener {
            onQuitClicked()
        }
    }

    private fun onDoneClicked() {
        if (isAValidName(layoutPlayer1, player1) && isAValidName(layoutPlayer2, player2)) {
            activity.onPlayersSet(player1, player2)
            dismiss()
        }
    }

    private fun onQuitClicked() {
        dismiss()
        activity.quitGame()
    }

    private fun isAValidName(layout: TextInputLayout, name: String) : Boolean {
        if (TextUtils.isEmpty(name)) {
            layout.isErrorEnabled = true
            layout.error = getString(R.string.game_dialog_empty_name)
            return false
        }
        if (player1.equals(player2, true)) {
            layout.isErrorEnabled = true
            layout.error = getString(R.string.game_dialog_same_names)
            return false
        }
        layout.isErrorEnabled = false
        layout.error = ""
        return true
    }

    private fun addTextWatchers() {
        etPlayer1.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                player1 = s.toString()
            }
        })
        etPlayer2.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                player2 = s.toString()
            }
        })
    }

}
