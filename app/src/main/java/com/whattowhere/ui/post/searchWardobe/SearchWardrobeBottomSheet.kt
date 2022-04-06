package com.whattowhere.ui.post.searchWardobe

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.myvagon.driver.ui.base.BaseBottomSheetDialogFragment
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.LayoutSearchWardrobeBottomSheetBinding
import com.whattowhere.ui.post.sharePostWithFriends.SharePostWithFriendModel
import com.whattowhere.ui.post.sharePostWithFriends.SharePostWithFriendsBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SearchWardrobeBottomSheet(
    override val layoutId: Int = com.whattowhere.R.layout.layout_search_wardrobe_bottom_sheet,
    override val bindingVariable: Int = BR.viewmodel
) : BaseBottomSheetDialogFragment<LayoutSearchWardrobeBottomSheetBinding, SearchWardrobeViewModel>() {
    lateinit var adapter: SearchWardrobeAdapter
    var friendsList = arrayListOf<SharePostWithFriendModel>()
    override fun setupObservable() {

    }


    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {

    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 85 / 100
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun init() {
        setWardrobe()

    }

    private fun setWardrobe() {
        val list = getList()
        adapter = SearchWardrobeAdapter()
        adapter.filterList(list)
        binding.rvWardrobe.adapter = adapter
        binding.tvSkipAndPublish.setOnClickListener{
            openFriendsSheet()
        }
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


    fun getList(): ArrayList<SearchWardrobeModel> {
        val list = arrayListOf<SearchWardrobeModel>()
        val listAdd = resources.getStringArray(R.array.wardrobe)
        for (i in listAdd) {
            val data = SearchWardrobeModel(
                ContextCompat.getDrawable(
                    activity,
                    R.drawable.ic_create_board_icon
                ), i
            )
            list.add(data)
        }
        return list
    }
    private fun openFriendsSheet() {
        val pickupLocationDialog = SharePostWithFriendsBottomSheet()
        val ft = childFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        pickupLocationDialog.show(childFragmentManager, "dialog")
    }
    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val list = getList()

        val filterList = arrayListOf<SearchWardrobeModel>()
        val nameOfFilter = arrayListOf<String>()
        //looping through existing elements
        for (s in list) {
            //if the existing elements contains the search input
            if (s.title.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                //adding the element to filtered list
                filterList.add(s)
            }
            nameOfFilter.add(s.title.lowercase(Locale.getDefault()))
        }
        if (!nameOfFilter.contains(text.lowercase(Locale.getDefault()))) {
            filterList.add(
                0,
                SearchWardrobeModel(
                    ContextCompat.getDrawable(
                        activity,
                        R.drawable.ic_create_board_icon
                    ), "Create $text"
                )
            )
        }

        adapter.filterList(filterList)

    }
}