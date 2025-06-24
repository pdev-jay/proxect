package com.pdevjay.proxect.presentation.screen.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectSearchBar(
    isSearchBarActive: MutableState<Boolean>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    DockedSearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true; traversalIndex = 0f },
        // Deprecated 메시지에서 제안하는 inputField 람다를 사용합니다.
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier
                    .fillMaxWidth(),
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    onQueryChange(searchQuery)
                },
                onSearch = { newQuery ->
                    // inputField 내에서 검색 액션 처리
                    isSearchBarActive.value = false
                    println("Searching for: $newQuery")
                    onSearch(newQuery)
                },
                expanded = isSearchBarActive.value, // active 상태 전달
                onExpandedChange = { isSearchBarActive.value = it }, // active 상태 변경 콜백 전달
                enabled = true, // enabled 상태 전달 (필요에 따라 변경)
                placeholder = { Text("검색어를 입력하세요") },
                leadingIcon = {
                    IconButton(
                        onClick = {
                            when (isSearchBarActive.value) {
                                true -> {
                                    isSearchBarActive.value = false
                                }

                                false -> {
                                    isSearchBarActive.value = true
                                }
                            }
                        }
                    ) {
                        if (isSearchBarActive.value) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Close icon"
                            )
                        } else {
                            Icon(Icons.Default.Search, contentDescription = "Search icon")
                        }
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear search")
                        }
                    }
                },
                colors = SearchBarDefaults.inputFieldColors(), // 기본 inputField 색상 사용
                interactionSource = null, // interactionSource 전달
            )
        },
        // SearchBar 자체에는 active, onActiveChange, content 등을 전달합니다.
        expanded = isSearchBarActive.value,
        onExpandedChange = { isSearchBarActive.value = it },
        // Deprecated 되지 않은 나머지 SearchBar 매개변수들은 유지
        shape = SearchBarDefaults.dockedShape,
        colors = SearchBarDefaults.colors(
        ),
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        // interactionSource = interactionSource, // 이 오버로드에서는 SearchBar 자체의 interactionSource는 제거될 수 있습니다.
        content = {
            // SearchBar가 활성화되었을 때 표시할 내용
            Text("검색 결과를 여기에 표시합니다.", modifier = Modifier.padding(16.dp))
        }
    )
}