package com.project.quizapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.project.quizapp.MainActivity
import com.project.quizapp.R
import com.project.quizapp.databinding.ActivityResultBinding
import com.project.quizapp.ui.viewmodel.QuizViewModel
import com.project.quizapp.utils.Constants.MAX_NO_OF_QUESTIONS

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private val viewModel: QuizViewModel by viewModels()

    companion object{
        const val SCORE_INCREASE = "extra_number"
        const val EXTRA_USER_NAME = "user_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USER_NAME)
             binding.tvName.text = username

        val scoreOne = intent.getIntExtra(SCORE_INCREASE, 0)

        val scoreText = getString(R.string.result, scoreOne)

        binding.scoreText.text = scoreText

        val totalQuestions = MAX_NO_OF_QUESTIONS
        if (scoreOne == totalQuestions) {
            binding.congratulatoryText.text = getString(R.string.game_won_message)
            binding.resultImageView.setImageResource(R.drawable.ic_trophy)
        } else {
            binding.congratulatoryText.text = getString(R.string.game_over_message)
            binding.resultImageView.setImageResource(R.drawable.trophy_two)
        }

        binding.newGameButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.shareButton.setOnClickListener {
            shareUserScore()
        }
    }

    private fun shareUserScore() {
        val scoreOne = intent.getIntExtra(SCORE_INCREASE, 0)

        val myScore = getString(R.string.my_result, scoreOne)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareMessage = "Hey! I scored $myScore points in this game. Come check it out!!!"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_score_title)))
    }
}
