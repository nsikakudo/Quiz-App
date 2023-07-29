package com.project.quizapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.quizapp.MainActivity
import com.project.quizapp.R
import com.project.quizapp.databinding.ActivityQuizQuestionsBinding
import com.project.quizapp.ui.viewmodel.QuizViewModel
import com.project.quizapp.utils.Constants
import com.project.quizapp.utils.Constants.MAX_NO_OF_QUESTIONS

class QuizQuestionsActivity : AppCompatActivity() {

    private val viewModel: QuizViewModel by viewModels()
    private var mUserName : String? = null


    private lateinit var binding: ActivityQuizQuestionsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        setUpObservers()

        viewModel.startQuiz()

        binding.nextButton.setOnClickListener {
            val selectedOption = binding.questionRadioGroup.checkedRadioButtonId
            if (selectedOption != -1) {
                val selectedAnswer = when (selectedOption) {
                    R.id.firstAnswerRadioButton -> binding.firstAnswerRadioButton.text.toString()
                    R.id.secondAnswerRadioButton -> binding.secondAnswerRadioButton.text.toString()
                    R.id.thirdAnswerRadioButton -> binding.thirdAnswerRadioButton.text.toString()
                    R.id.fourthAnswerRadioButton -> binding.fourthAnswerRadioButton.text.toString()
                    else -> ""
                }
                binding.questionRadioGroup.clearCheck()

                val isAnswerCorrect = viewModel.isAnswerCorrect(selectedAnswer)

                if (isAnswerCorrect) {
                    viewModel.updateScore()
                }
                if (viewModel.hasMoreQuestions()) {
                    viewModel.nextQuestion()
                } else {
                    val score = viewModel.score.value ?: 0

                    val intent = Intent(this, ResultActivity::class.java)

                    intent.putExtra(Constants.USER_NAME, mUserName)
                    intent.putExtra(ResultActivity.SCORE_INCREASE, score)
                    startActivity(intent)
                }
            }
        }
        binding.quitGameText.setOnClickListener {
            exitGame()
        }
    }

    private fun setUpObservers() {
        viewModel.currentQuestionCount.observe(this) { questionCount ->
            val totalQuestions = MAX_NO_OF_QUESTIONS
            binding.questionIndicatorText.text =
                getString(R.string.question_indicator_text, questionCount, totalQuestions)

            binding.nextButton.text = if (questionCount < totalQuestions) {
                getString(R.string.next)
            } else {
                getString(R.string.submit)
            }
            updateDashes(questionCount)
        }
        viewModel.currentQuestion.observe(this) { question ->
            binding.questionText.text = question.question
            binding.firstAnswerRadioButton.text = question.options[0]
            binding.secondAnswerRadioButton.text = question.options[1]
            binding.thirdAnswerRadioButton.text = question.options[2]
            binding.fourthAnswerRadioButton.text = question.options[3]
        }
    }

    private fun exitGame() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.quit_game))
            .setMessage(getString(R.string.quit_confirmation))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun updateDashes(questionCount: Int) {
        val progressView = listOf(binding.dashOne, binding.dashTwo, binding.dashThree)

        for (i in progressView.indices) {
            val backgroundDrawable = if (i < questionCount) {
                R.drawable.red_dash
            } else {
                R.drawable.gray_dash
            }
            progressView[i].setBackgroundResource(backgroundDrawable)
        }
    }

    }

