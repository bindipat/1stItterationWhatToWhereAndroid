package com.whattowhere.ui.post.sharePostWithFriends

import android.text.Editable
import android.text.TextWatcher
import com.myvagon.driver.ui.base.BaseBottomSheetDialogFragment
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.LayoutSharePostBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SharePostWithFriendsBottomSheet(
    override val layoutId: Int = R.layout.layout_share_post_bottom_sheet,
    override val bindingVariable: Int = BR.viewmodel
) : BaseBottomSheetDialogFragment<LayoutSharePostBottomSheetBinding, SharePostWithFriendsViewModel>() {
    lateinit var adapter: SharePostWithFriendsAdapter
    var friends = arrayListOf<SharePostWithFriendModel>()

    override fun init() {
        setFriendsListAdapter()
    }

    private fun setFriendsListAdapter() {
        getFriendList()
        adapter =
            SharePostWithFriendsAdapter(object : SharePostWithFriendsAdapter.OnClickSharePost {
                override fun onSharePost(data: SharePostWithFriendModel, position: Int) {
                    friends.set(position, data)

                }

            })
        adapter.filterList(friends)
        binding.rvWardrobe.adapter = adapter
        binding.edtUserName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                filter(s.toString())

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {


            }

            override fun afterTextChanged(s: Editable) {


            }
        })

    }

    override fun setupObservable() {

    }

    fun getFriendList(): ArrayList<SharePostWithFriendModel> {
        friends.clear()
        for (i in 0..20) {
            val data = SharePostWithFriendModel(
                image = "https://i.picsum.photos/id/1025/4951/3301.jpg?hmac=_aGh5AtoOChip_iaMo8ZvvytfEojcgqbCH7dzaz-H8Y",
                "jassica_voltr",
                "Jassica Volter",
                false
            )
            friends.add(data)
        }
        return friends
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data

        val filterList = arrayListOf<SharePostWithFriendModel>()

        //looping through existing elements
        for (s in friends) {
            //if the existing elements contains the search input
            if (s.fullName.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
                || s.userName.lowercase().contains(text.lowercase())
            ) {
                //adding the element to filtered list
                filterList.add(s)
            }

        }

        adapter.filterList(filterList)

    }

}