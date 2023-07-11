package com.quizmatch.app.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.quizmatch.app.BR
import com.quizmatch.app.R
import com.quizmatch.app.base.BaseViewHolder
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.databinding.RowItemUserListBinding
import com.quizmatch.app.utils.OnItemClickListener

class UserListAdapter(val mViewModel: UserListViewModel, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<BaseViewHolder<User>>() {
    private var isLoaderVisible = false
    private val mDataList = mutableListOf<User>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<User> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ViewDataBinding = DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.row_item_user_list,
                    parent,
                    false
                )
                return UserListViewHolder(binding as RowItemUserListBinding)

        }


    override fun onBindViewHolder(holder: BaseViewHolder<User>, position: Int) {

        if (holder is UserListViewHolder) {
            val data = mDataList[position]
            holder.bindData(data)
        }


    }


    fun updateList(list: List<User>) {
        mDataList.clear()
        mDataList.addAll(list)
        notifyDataSetChanged()
    }


    private fun getItem(position: Int): User {
        return mDataList[position]
    }

    fun addLoading() {
        isLoaderVisible = true
        mDataList.add(User())
        notifyItemInserted(mDataList.size - 1)

    }



    override fun getItemCount(): Int {
        return mDataList.size
    }
    inner class UserListViewHolder(val binding: RowItemUserListBinding): BaseViewHolder<User>(binding.root){
        fun bindData(itemData:User) {
            binding.setVariable(BR.viewModel, mViewModel)
            binding.setVariable(BR.itemData, itemData)
            binding.cvPlayer.setOnClickListener {
                // Get the position of the clicked item
                val position = adapterPosition

                // Invoke the onItemClick method of the click listener interface
                itemClickListener.onItemClick(position,mDataList[position])


            }

            binding.executePendingBindings()
        }




    }
    inner class ProgressViewHolder(binding: ViewDataBinding) :
        BaseViewHolder<User>(binding.root)


}