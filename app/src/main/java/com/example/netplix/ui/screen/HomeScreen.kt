package com.example.netplix.ui.screen

import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.netplix.data.model.Movie
import com.example.netplix.ui.screen.HomeScreen.CarouselItem
import com.example.netplix.ui.screen.bottomSheet.DetailSheet
import com.example.netplix.ui.screen.utility.IScreen
import com.example.netplix.ui.theme.NetplixTheme
import com.example.netplix.utils.getImageLink
import com.example.netplix.utils.safe
import com.example.netplix.viewmodel.HomeViewModel
import com.example.netplix.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.placeholder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
object HomeScreen : IScreen {

    override val routeName: String
        get() = "home"

    @Composable
    override fun Screen(navController: NavController, args: Bundle?) {

        val viewModel = hiltViewModel<HomeViewModel>()

        Column(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.9f))
                .fillMaxSize()
        ) {
            TopAppBar() {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { /*Unimplemented*/ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                    Text(text = "Netplix", modifier = Modifier.weight(1f))
                    IconButton(onClick = { navController.navigate(SearchScreen.routeName) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Navigate to search"
                        )
                    }
                }
            }
            Column(
                Modifier.verticalScroll(rememberScrollState())
            ) {
                Carousel(list = viewModel.discoverMovie, pagerState = rememberPagerState()) {
                    navController.currentBackStackEntry?.savedStateHandle
                        ?.set("id", viewModel.discoverMovie[it].id)
                    navController.navigate(
                        DetailSheet.routeName
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Popular", color = Color.Yellow)
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalScrollMovie(list = viewModel.popularMovie) {
                    navController.currentBackStackEntry?.savedStateHandle
                        ?.set("id", viewModel.popularMovie[it].id)
                    navController.navigate(
                        DetailSheet.routeName
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Top Rated", color = Color.Yellow)
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalScrollMovie(list = viewModel.topRatedMovie) {
                    navController.currentBackStackEntry?.savedStateHandle
                        ?.set("id", viewModel.topRatedMovie[it].id)
                    Log.d("CLICKEDIT", it.toString())
                    navController.navigate(
                        DetailSheet.routeName
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    @Composable
    fun Carousel(
        list : List<Movie>,
        pagerState: PagerState,
        onClick: (Int) -> Unit
    ) {
        val shownList = if(list.size > 5) list.subList(0,5) else list
        val scope = rememberCoroutineScope()

        HorizontalPager(
            count = shownList.size,
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = true,
            modifier = Modifier.fillMaxWidth()
        ) { item ->
            CarouselItem(item = shownList[item]) { onClick(shownList[item].id.safe()) }
        }

        LaunchedEffect(true) {
            delay(5000)
            while (true) {
                scope.launch {
                    with(pagerState) {
                        val bool = currentPage >= pageCount - 1
                        Log.d("PAGER", currentPage.toString())
                        if(bool) {
                            animateScrollToPage(0)
                        } else {
                            animateScrollToPage(currentPage + 1)
                        }
                    }
                }
                delay(5000)
            }
        }
    }

    @Composable
    fun CarouselItem(item : Movie, onClick: () -> Unit = {}) {
        val isLoading = remember { mutableStateOf(true) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            SubcomposeAsyncImage(
                model = getImageLink(item.backdropPath ?: item.posterPath.safe()),
                contentDescription = "carousel",
                modifier = Modifier
                    .placeholder(
                        isLoading.value,
                        color = Color.Yellow,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth()
            ) {
                val state = painter.state
                if(state !is AsyncImagePainter.State.Success) {
                    isLoading.value = true
                    CircularProgressIndicator()
                } else {
                    SubcomposeAsyncImageContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.FillWidth,
                    )
                    isLoading.value = false
                }
            }
            Text(
                text = item.title ?: item.originalTitle.safe(),
                modifier = Modifier
                    .placeholder(
                        isLoading.value,
                        Color.LightGray
                    )
                    .align(Alignment.BottomStart)
                    .background(Color.White.copy(0.3f))
                    .padding(8.dp),
                fontWeight = W700,
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
    }

    @Composable
    fun HorizontalScrollMovie(
        list: List<Movie>,
        onClick: (Int) -> Unit
    ) {
        val isLoading = remember { mutableStateOf(false) }
        LazyRow() {
            item { Spacer(modifier = Modifier.width(8.dp)) }
            items(if(isLoading.value) 10 else list.size) {
                MovieItem(item = list[it]) { onClick(it) }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }

    @Composable
    fun MovieItem(item: Movie, onClick: () -> Unit) {
        val isLoading = remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .height(160.dp)
                .width(108.dp)
                .border(
                    1.dp, Color.Yellow, RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(model = getImageLink(item.posterPath ?: item.backdropPath.safe()), contentDescription = "poster") {
                val state = painter.state
                if(state !is AsyncImagePainter.State.Success) {
                    CircularProgressIndicator()
                    isLoading.value = true
                } else {
                    SubcomposeAsyncImageContent()
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

@Preview
@Composable
fun PreviewCarousel() {
//    val movie = Movie("title", "https://play-lh.googleusercontent.com/ZyWNGIfzUyoajtFcD7NhMksHEZh37f-MkHVGr5Yfefa-IX7yj9SMfI82Z7a2wpdKCA=w240-h480")
//    CarouselItem(movie)

}