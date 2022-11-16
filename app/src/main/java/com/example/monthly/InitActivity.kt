package com.example.monthly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monthly.ui.theme.MonthlyTheme

class InitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

//@Composable
//fun InitUser() {
//    Scaffold(
//        bottomBar = { SootheBottomNavigation()}
//    ){ padding ->
//        InnerScreen(Modifier.padding(padding))
//    }
//}
//
//@Composable
//fun InnerScreen(modifier: Modifier = Modifier) {
//    Column(
//        modifier
//            .verticalScroll(rememberScrollState())
//            .padding(vertical = 16.dp)
//    ) {
//        SearchBar(Modifier.padding(horizontal = 16.dp))
//        HomeSection(title = R.string.align_your_body) {
//            AlignYourBodyRow()
//        }
//        HomeSection(title = R.string.favorite_collections) {
//            FavoriteCollectionsGrid()
//        }
//    }
//}
//
//@Preview(widthDp = 360, heightDp = 640)
//@Composable
//fun MyInitPreview() {
//    InitUser()
//}