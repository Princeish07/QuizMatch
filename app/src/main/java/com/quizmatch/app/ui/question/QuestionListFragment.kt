package com.quizmatch.app.ui.question

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.Manifest.permission.*
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.quizmatch.app.R
import com.quizmatch.app.data.model.firebase.ScoreDetail
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.databinding.FragmentQuestionListBinding
import com.quizmatch.app.utils.*
import com.quizmatch.app.utils.Constants.MATCH_ID_KEY
import com.quizmatch.app.utils.Constants.USER_DETAIL_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class QuestionListFragment : Fragment() {

    lateinit var binding: FragmentQuestionListBinding
    lateinit var bitmap: Bitmap
    private val PERMISSION_REQUEST_CODE: Int = 200

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

        if (!checkPermission(context = requireContext())) {
            requestPermission(requireActivity(),PERMISSION_REQUEST_CODE)
        }
        showPrompt()
        showDialog()
        showLoader()
        observeQuestionList()
        observeBool()

        viewModel.getQuestionList()
        viewModel.matchId.value = arguments?.getString(MATCH_ID_KEY) as String
        viewModel.opponentUser.value = arguments?.getSerializable(USER_DETAIL_KEY) as User

        binding.myToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            val checkedButton: MaterialButton? = group.findViewById(checkedId)
            if (isChecked && checkedButton != null) {
                viewModel.loader.value = true
                viewModel.checkAnswer(checkedButton.text.toString())
            }
        }
    }



    fun showPrompt(){
        viewModel.promptMessage.observe(requireActivity()) {it -> it as Int
            this.showToast(it)
        }
    }

    fun showDialog() {
        viewModel.finalScore.observe(viewLifecycleOwner) { it ->
            if (it != ScoreDetail("", "")) {
                requireActivity().showMatchCompleteDialog(yourScore = it.yourScore,
                    opponentScore = it
                        .opponentScore,
                    onShare = {
                        bitmap = it


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            if (Environment.isExternalStorageManager()) {
                                requireActivity().storeValueInDevice(bitmap, onSuccess = {
                                    if (it) {
                                        viewModel.questionIndex = 0
                                        viewModel.finalScore.value = ScoreDetail("","")

                                        requireActivity().onBackPressed()
                                    } else {
                                        viewModel.promptMessage.value = R.string.invalid
                                    }
                                })
                            } else {
                                requestPermission(requireActivity(), PERMISSION_REQUEST_CODE)

                            }

                        }
                    })


            }
        }
    }

//    override fun onStop() {
//        super.onStop()
//        viewModel.finalScore.removeObserver {
//
//        }
//    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (writeStorage) {
                    Toast.makeText(requireContext(), "Permission Granted..", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Permission Denied.", Toast.LENGTH_SHORT).show()
                }
            }
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

    fun showLoader(){
        viewModel.loader.observe(requireActivity()) {it ->
            if(it){
                binding.rlProgress.visibility = View.VISIBLE
            }else{
                binding.rlProgress.visibility = View.GONE

            }
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


    }

}