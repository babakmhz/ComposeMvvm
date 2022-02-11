package com.babakmhz.composemvvm.presentation.ui.photosList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.babakmhz.composemvvm.presentation.MainViewModel
import com.babakmhz.composemvvm.presentation.ui.base.BaseActivity
import com.babakmhz.composemvvm.presentation.ui.base.BaseFragment
import com.babakmhz.composemvvm.presentation.ui.photosList.components.photoListItem


@ExperimentalFoundationApi
class PhotosListFragment : BaseFragment() {

    private val viewModel: MainViewModel by lazy {
        getSharedViewModel(requireActivity() as BaseActivity, MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                contentView(viewModel = viewModel)
            }
        }
    }


    @Composable
    fun contentView(viewModel: MainViewModel) {
        photosListView(viewModel = viewModel)
    }
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun photosListView(viewModel: MainViewModel) {
    val loading by viewModel.loadingState.observeAsState()
    val photos by viewModel.photosState.observeAsState()
    val error by viewModel.errorState.observeAsState()

    Box(contentAlignment = Alignment.Center) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier.padding(4.dp)
        ) {
            photos?.let {
                items(it.size) { index ->
                    val item = photos!![index]
                    photoListItem(item) {
                        // navigate
                    }
                }
            }
        }

        if (loading == true)
            CircularProgressIndicator(
                modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            )
    }
}

