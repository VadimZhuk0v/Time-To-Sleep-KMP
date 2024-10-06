package com.vadmax.timetosleep.ui.main.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.ui.theme.AppTheme
import kotlinx.coroutines.launch
import qrgenerator.qrkitpainter.QrKitBrush
import qrgenerator.qrkitpainter.rememberQrKitPainter
import qrgenerator.qrkitpainter.solidBrush

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BackdropContent(
    connectQRData: String,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()

    Column {
        HomeBackdropTabBar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onDownloadClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            },
            onPairClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            },
            isPairTab = pagerState.currentPage == 0,
        )
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            when (it) {
                0 -> ConnectDeviceContent(
                    connectQRData = connectQRData,
                )

                else -> DownloadAppContent()
            }
        }
    }
}

@Composable
private fun ConnectDeviceContent(
    connectQRData: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val painter = rememberQrKitPainter(
            data = connectQRData,
            options = {
                qrColors {
                    darkColorBrush = QrKitBrush.solidBrush(Color.White)
                }
            }
        )
        Text(
            text = "Pair android app via QR code",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.height(20.dp))
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
private fun DownloadAppContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Will be available soon",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun ConnectDeviceContentPreview() {
    AppTheme {
        ConnectDeviceContent(connectQRData = "Some data")
    }
}