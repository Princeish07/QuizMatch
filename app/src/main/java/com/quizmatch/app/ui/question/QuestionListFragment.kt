package com.quizmatch.app.ui.question

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.quizmatch.app.R
import com.quizmatch.app.data.model.api.QuestionListResponse
import com.quizmatch.app.databinding.FragmentQuestionListBinding
import com.quizmatch.app.ui.landing.LandingActivityViewModel
import com.quizmatch.app.utils.Constants.MATCH_ID_KEY
import com.quizmatch.app.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuestionListFragment : Fragment() {

    lateinit var binding: FragmentQuestionListBinding

    @Inject
    lateinit var viewModel: QuestionListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate view
        binding = FragmentQuestionListBinding.inflate(inflater, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question_list, container, false);
    return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPrompt()
        observeQuestionList()
        observeBool()

        viewModel.getQuestionList()
        viewModel.matchId.value = arguments?.getString(MATCH_ID_KEY) as String
      //  viewModel.getScoreDetail(arguments?.getString(MATCH_ID_KEY) as String)

        binding.myToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            val checkedButton: MaterialButton? = group.findViewById(checkedId)
            if (isChecked && checkedButton != null) {
                viewModel.checkAnswer(checkedButton.text.toString())
            }
        }
    }



    fun showPrompt(){
        viewModel.promptMessage.observe(requireActivity()) {it -> it as Int
            this.showToast(it)
        }
    }
    fun observeQuestionList(){
        viewModel.questionListResponse.observe(requireActivity()) {it ->
            viewModel.questionIndex = 0
            manageView()
        }
    }
    fun observeBool(){
        viewModel.scoreUpdate.observe(requireActivity()) {it ->
            manageView()
        }
    }
    private fun manageView(){

        viewModel.questionResult = viewModel.questionListResponse.value?.results?.get(viewModel.questionIndex)!!
        val optionList = viewModel.questionResult.incorrect_answers
        optionList.add(viewModel.questionResult.correct_answer)
        optionList.shuffle().apply {
            if(optionList.count() == 2) {
                binding.option1.text = optionList.get(0)
                binding.option2.text = optionList.get(1)
                binding.option3.visibility = View.GONE
                binding.option4.visibility = View.GONE

            }
            else if(optionList.count() == 3) {
                binding.option1.text = optionList.get(0)
                binding.option2.text = optionList.get(1)
                binding.option3.text = optionList.get(2)
                binding.option3.visibility = View.VISIBLE
                binding.option4.visibility = View.GONE
            } else{
                binding.option1.text = optionList.get(0)
                binding.option2.text = optionList.get(1)
                binding.option3.text = optionList.get(2)
                binding.option4.text = optionList.get(3)
                binding.option3.visibility = View.VISIBLE
                binding.option4.visibility = View.VISIBLE
            }
        }
        binding.tvQuestion.text = viewModel.questionResult.question
        binding.tvQuesNo.text = (viewModel.questionIndex+1).toString()


    }

}