package com.quizmatch.app.utils

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.quizmatch.app.R
import com.quizmatch.app.databinding.DialogQuizCompleteBinding


private var mAppThemeDialog: AlertDialog? = null

fun Activity.showMatchCompleteDialog(yourScore:String,opponentScore:String,onShare:(Bitmap)
->Unit){
    if (mAppThemeDialog != null && mAppThemeDialog?.isShowing == true) {
        mAppThemeDialog?.dismiss()
        return
    }
    if (mAppThemeDialog != null && mAppThemeDialog?.isShowing == true) {
        mAppThemeDialog?.dismiss()
    }


    val binding: DialogQuizCompleteBinding =
        DataBindingUtil.inflate(this.layoutInflater, com.quizmatch.app.R.layout.dialog_quiz_complete, null, false)
    val builder: AlertDialog.Builder?
    builder = AlertDialog.Builder(this)
    //set view
    builder.setView(binding.root)


        binding.tvUrScore.text = yourScore


    binding.tvOpponentScore.text = opponentScore

    if(yourScore.toInt() > opponentScore.toInt()){
        binding.tvWinner.text = "You Won"
        Glide.with(this)
            .load(com.quizmatch.app.R.drawable.winner)
            .into(binding.ivWinnerLogo)
    }else{
        binding.tvWinner.text = "You Loss"
        Glide.with(this)
            .load(com.quizmatch.app.R.drawable.sad)
            .into(binding.ivWinnerLogo)
    }
    val buttonShare = binding.btnShare
    buttonShare.setOnClickListener {
        mAppThemeDialog?.dismiss()
        val bitmap =
            Bitmap.createBitmap(binding.cvCard.width, binding.cvCard.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val p = binding.cvCard.draw(canvas)// Implement the share functionality here
        onShare(bitmap)

    }
    mAppThemeDialog = builder.create()
    mAppThemeDialog!!.show()

}