package com.gq.basicm3.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import com.gq.basicm3.common.ToastCommon
import com.gq.basicm3.viewmodel.LogViewModel
import java.io.File

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LogListCompose(
    modifier: Modifier = Modifier,
    logViewModel: LogViewModel = hiltViewModel()
) {

    var refreshing by remember { mutableStateOf(false) }
    val logFilesState = remember { mutableStateListOf<File>() }
    val owner = LocalLifecycleOwner.current
    val observer by remember {
        mutableStateOf(Observer<MutableList<File>> { fList ->
            refreshing = false
            logFilesState.clear()
            logFilesState.addAll(fList)
        })
    }
    var logContentState by remember { mutableStateOf("") }
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        refreshing = true
        logViewModel.queryLogsFiles().observe(owner, observer)
    })

    LaunchedEffect(key1 = true, block = {
        logViewModel.queryLogsFiles().observe(owner, observer)
    })

    Box(modifier = modifier
        .fillMaxWidth()
        .pullRefresh(pullRefreshState)) {
        LazyColumn(modifier = Modifier.fillMaxWidth(), content = {
            items(logFilesState) { file ->
                Row(
                    modifier = Modifier
                        .clickable {
                            logViewModel
                                .readLogContent(file)
                                .observe(owner, Observer { str ->
                                    logContentState = str
                                })
                        }
                        .padding(horizontal = 14.dp)
                        .padding(top = 14.dp)
                ) {
                    Column {
                        Text(text = file.name)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${file.length()} bytes", fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = {
                        ToastCommon.showCenterToast("Can't delete temporarily")
                    }) {
                        Text(text = "Delete")
                    }
                }
            }
        })

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        CheckLogTextCompose(logContentState) {
            logContentState = ""
        }
    }
}

@Composable
private fun CheckLogTextCompose(str: String, onCloseClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(content = {
            item {
                Text(text = str)
            }
        })

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 14.dp), onClick = { onCloseClick() }) {
            Text(text = "关闭")
        }
    }
}