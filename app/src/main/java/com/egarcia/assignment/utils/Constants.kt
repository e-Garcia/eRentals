package com.egarcia.assignment.utils

const val BASE_URL: String = "https://search.outdoorsy.co"
const val PAGE_SIZE = 5

sealed class ProgressStatus {
    object Success : ProgressStatus()
    object Loading : ProgressStatus()
    data class Error(val throwable: Throwable?) : ProgressStatus()
}