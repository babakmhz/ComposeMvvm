package com.babakmhz.composemvvm.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.babakmhz.composemvvm.data.db.model.Photo
import com.babakmhz.composemvvm.presentation.ui.base.BaseFragment
import com.babakmhz.composemvvm.presentation.ui.components.photoListItem


@ExperimentalFoundationApi
class DetailFragment : BaseFragment() {

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                contentView(photo = args.photo)
            }
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun contentView(photo: Photo) {
        photoListItem(photo = photo, onPhotoClicked = {}, showDetails = true)
    }
}
