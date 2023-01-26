package com.example.netplix.ui.screen

import android.os.Bundle
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.netplix.data.model.Movie
import com.example.netplix.ui.screen.utility.IScreen
import com.example.netplix.ui.theme.NetplixTheme
import com.example.netplix.utils.getImageLink
import com.example.netplix.utils.safe
import com.example.netplix.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

object SearchScreen : IScreen {
    override val routeName: String
        get() = "search"

    @Composable
    override fun Screen(navController: NavController, args: Bundle?) {

        val searchViewModel = hiltViewModel<SearchViewModel>()
        val scope = rememberCoroutineScope()

        Column() {
            TopAppBar() {
                Row() {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "menu"
                        )
                    }
                    OutlinedTextField(
                        value = searchViewModel.searchKeyword.value,
                        onValueChange = {
                            searchViewModel.searchKeyword.value = it
                            scope.launch {
                                searchViewModel.searchMovie()
                            }
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                        },
                        placeholder = {
                            Text("Search Movies")
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White.copy(alpha = 0.7f),
                            leadingIconColor = Color.Black
                        )

                    )

                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalArrangement = Arrangement.Center,
            ) {
                items(searchViewModel.searchResult.size) {
                    if ((it + 1) >= searchViewModel.currentPage * 10) {
                        SideEffect {
                            scope.launch {
                                searchViewModel.nextPage()
                            }
                        }
                    }
                    MovieItem(item = searchViewModel.searchResult[it])
                }
            }
        }
    }

    @Composable
    fun MovieItem(item: Movie) {
        val isLoading = remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .defaultMinSize(minHeight = 300.dp)
                .clip(RoundedCornerShape(12.dp))
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = getImageLink(item.posterPath ?: item.backdropPath.safe()),
                contentDescription = "poster",
                modifier = Modifier.fillMaxWidth()
            ) {
                val state = painter.state
                if(state !is AsyncImagePainter.State.Success) {
                    CircularProgressIndicator()
                    isLoading.value = true
                } else {
                    SubcomposeAsyncImageContent(
                        modifier = Modifier.wrapContentHeight(),
                        contentScale = ContentScale.FillWidth
                    )
                    isLoading.value = false
                }
            }
            Text(
                text = item.title ?: item.originalTitle.safe(),
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomStart),
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NetplixTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            SearchScreen.Screen(navController = rememberNavController(), args = Bundle())
        }
    }
}



