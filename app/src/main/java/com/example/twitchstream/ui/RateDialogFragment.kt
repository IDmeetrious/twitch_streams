package com.example.twitchstream.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.arch.core.executor.TaskExecutor
import androidx.fragment.app.DialogFragment
import com.example.twitchstream.R
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode

private const val TAG = "RateDialogFragment"
class RateDialogFragment : DialogFragment() {
    private lateinit var button: Button
    private lateinit var one: ImageButton
    private lateinit var two: ImageButton
    private lateinit var three: ImageButton
    private lateinit var four: ImageButton
    private lateinit var five: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_rateus, container, false)
        mView.let {
            button = it.findViewById(R.id.fragment_rateus_btn)
            one = it.findViewById(R.id.fragment_rateus_star_one)
            two = it.findViewById(R.id.fragment_rateus_star_two)
            three = it.findViewById(R.id.fragment_rateus_star_three)
            four = it.findViewById(R.id.fragment_rateus_star_four)
            five = it.findViewById(R.id.fragment_rateus_star_five)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.let {
            it.setCanceledOnTouchOutside(true)
            it.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded)
        }

        button.setOnClickListener {
            Toast.makeText(requireContext(), "Thank you for feedback!", Toast.LENGTH_SHORT)
                .show()
            dismiss()
            val playMarket =
                Uri.parse("http://play.google.com/store/search?q=twitch&c=apps")
            val intent = Intent(Intent.ACTION_VIEW)
                .setData(playMarket)
            startActivity(intent)
        }

        one.setOnClickListener {
            it.setBackgroundResource(R.drawable.ic_star_24)
        }
        two.setOnClickListener {
            one.setBackgroundResource(R.drawable.ic_star_24)
            it.setBackgroundResource(R.drawable.ic_star_24)
        }
        three.setOnClickListener {
            one.setBackgroundResource(R.drawable.ic_star_24)
            two.setBackgroundResource(R.drawable.ic_star_24)
            it.setBackgroundResource(R.drawable.ic_star_24)
        }
        four.setOnClickListener {
            one.setBackgroundResource(R.drawable.ic_star_24)
            two.setBackgroundResource(R.drawable.ic_star_24)
            three.setBackgroundResource(R.drawable.ic_star_24)
            it.setBackgroundResource(R.drawable.ic_star_24)
        }
        five.setOnClickListener {

            one.setBackgroundResource(R.drawable.ic_star_24)
            two.setBackgroundResource(R.drawable.ic_star_24)
            three.setBackgroundResource(R.drawable.ic_star_24)
            four.setBackgroundResource(R.drawable.ic_star_24)
            it.setBackgroundResource(R.drawable.ic_star_24)
        }
    }
}