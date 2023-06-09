package com.example.photoweather.ui.view.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.photoweather.ui.theme.Shapes
import com.example.photoweather.ui.theme.Typography
import com.example.component.LoadingBar
import com.example.component.ShowToast
import com.example.data.model.CarSearchResponseItem
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import com.example.photoweather.R


@Composable
fun CarSearchScreen(
    carsViewModel: CarsViewModel = hiltViewModel(),
    onClick: (String) -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.cars_screen_title),
                style = Typography.h4,
                modifier = Modifier.padding(24.dp),
            )
            CarsContent(vm = carsViewModel, onImageClick = onClick)
        }
    }
}

@Composable
fun CarsContent(vm: CarsViewModel, onImageClick: (String) -> Unit) {
    vm.state.value.let { state ->
        when (state) {
            is Loading -> LoadingBar()
            is CarsListUiStateReady -> state.cars?.let { BindList(it, onImageClick = onImageClick) }
            is CarsListUiStateError -> state.error?.let { ShowToast(it) }
        }
    }
}

@Composable
fun CarImage(item: CarSearchResponseItem) {
    item.images?.let {
        Image(
            painter = rememberAsyncImagePainter(it.first().url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
        )
    }
}

@Composable
fun ListItem(item: CarSearchResponseItem, onClick: (CarSearchResponseItem) -> Unit) {
    Card(
        shape = Shapes.large,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
            .clickable { onClick.invoke(item) },
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
        ) {

            CarImage(item)
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = "€${item.price}",
                    fontWeight = FontWeight.Bold,
                    style = Typography.subtitle2,
                )
                Text(
                    text = "${item.make} ${item.model}",
                    fontWeight = FontWeight.Bold,
                    style = Typography.subtitle1,
                )

                Text(
                    text = item.fuel,
                    style = Typography.caption,
                )
                Spacer(modifier = Modifier.height(8.dp))

                item.colour?.let {
                    Text(
                        text = stringResource(R.string.cars_screen_color, it),
                        style = Typography.subtitle2,
                    )
                }
                item.mileage?.let {
                    Text(
                        text = stringResource(R.string.cars_screen_miles, it),
                        style = Typography.subtitle2,
                    )
                }

                item.seller?.let {
                    Text(
                        text = stringResource(
                            R.string.cars_screen_miles,
                            "${it.type} ${it.phone} ${it.city}"
                        ),
                        style = Typography.caption,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.description,
                    style = Typography.caption,
                    maxLines = 2
                )

            }
        }
    }
}

@Composable
fun BindList(list: List<CarSearchResponseItem>, onImageClick: (String) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)) {
        items(
            items = list,
            itemContent = {
                ListItem(it, onClick = {
                    it.images?.first()?.url?.let {
                        val encodedUrl = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                        onImageClick.invoke(encodedUrl)
                    }
                })
            })
    }
}


