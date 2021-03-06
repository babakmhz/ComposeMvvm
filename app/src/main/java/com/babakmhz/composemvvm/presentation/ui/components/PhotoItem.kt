package com.babakmhz.composemvvm.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
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
    photo: Photo,
    isThumbnail: Boolean = false,
    showDetails: Boolean = false,
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
                    data = if (isThumbnail) photo.thumbnailUrl else photo.url,
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            photoDetailItemText(text = photo.title)

            if(showDetails){
                photoDetailItemText(text = stringResource(id = R.string.id_d, photo.id.toInt()))
                photoDetailItemText(text = stringResource(id = R.string.album_id_d, photo.albumId))
            }

        }
    }
}

@Composable
fun photoDetailItemText(text: String) {
    Text(
        text = text, fontSize = 18.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun photoItemPreview() {
    val photo = Photo(
        10,
        id = 100,
        thumbnailUrl = "https://via.placeholder.com/150/24f355",
        title = "hellooo"
    )
    photoListItem(photo) {

    }
}

