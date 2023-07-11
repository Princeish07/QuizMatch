package com.quizmatch.app.ui.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.quizmatch.app.R
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.databinding.FragmentUserListBinding
import com.quizmatch.app.utils.Constants.MATCH_ID_KEY
import com.quizmatch.app.utils.Constants.USER_DETAIL_KEY
import com.quizmatch.app.utils.OnItemClickListener
import com.quizmatch.app.utils.navigateToQuestionListScreen
import com.quizmatch.app.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : Fragment() {

    lateinit var binding:FragmentUserListBinding

    val mAdapter by lazy { UserListAdapter(viewModel,object : OnItemClickListener{
        override fun onItemClick(position: Int,itemDetail: User) {
            requireActivity().showToast(message = "Clicked Item $position")
            viewModel.createMatch(itemDetail)
//            viewModel.getUserList()
//            requireActivity().navigateToUserListScreen()
        }

    }) }

    @Inject
    lateinit var viewModel: UserListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserListBinding.inflate(inflater)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_list,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        showPrompt()
        observeUserListResponse()
        observeMatchCreation()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserList()

    }

    fun initRecyclerView() {
        //layout manager
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rcvUserList.layoutManager = llm
        binding.rcvUserList.itemAnimator = DefaultItemAnimator()
        binding.rcvUserList.adapter = mAdapter
    }
    fun observeMatchCreation(){
        viewModel.matchId.observe(requireActivity()) {it ->
            val bundle = Bundle()
            bundle.putString(MATCH_ID_KEY,it)
            bundle.putSerializable(USER_DETAIL_KEY, viewModel.opponentUser.value)
            requireActivity().navigateToQuestionListScreen(bundle = bundle)
        }
    }
    fun showPrompt(){
        viewModel.promptMessage.observe(requireActivity()) {it -> it as Int
            this.showToast(resId = it)
        }
    }
    fun observeUserListResponse(){
        viewModel.userListResponse.observe(requireActivity()) {it ->
            if(it.isNotEmpty()) {
                mAdapter.updateList(it)
            }
        }
    }
}