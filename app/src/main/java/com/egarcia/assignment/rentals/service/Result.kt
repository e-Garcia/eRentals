package com.egarcia.assignment.rentals.service

sealed class Response<out R> {
    class Success<R>(val value: R) : Response<R>()
    class Failure(val error: Throwable) : Response<Nothing>()
}
