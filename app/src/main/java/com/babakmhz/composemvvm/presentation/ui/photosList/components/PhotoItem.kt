package com.babakmhz.composemvvm.presentation.ui.photosList.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.babakmhz.composemvvm.R
import com.babakmhz.composemvvm.data.db.model.Photo
import timber.log.Timber


@ExperimentalMaterialApi
@Composable
fun photoListItem(
    photo:Photo,
    onPhotoClicked: Photo.() -> Unit
) {

    Timber.i("loading image")
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                onPhotoClicked.invoke(photo)
            },
        contentAlignment = Alignment.Center
    ) {
        Card(shape = RoundedCornerShape(8.dp)) {
            Image(
                painter = rememberImagePainter(
                    data = photo.thumbnailUrl,
                    builder = {
                        placeholder(R.drawable.ic_launcher_background)
                        crossfade(true)
                        crossfade(600)
                        scale(Scale.FILL)
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        Text(
            text = photo.title, fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun photoItemPreview() {
    val photo = Photo(0, thumbnailUrl = "https://via.placeholder.com/150/24f355")
    photoListItem(photo) {

    }
}

