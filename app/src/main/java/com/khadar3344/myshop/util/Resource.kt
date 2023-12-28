package com.khadar3344.myshop.util


sealed class Resource<out R> {
    data class Success<out R>(val data: R): Resource<R>()
    data class Failure<R>(val exception: R): Resource<Nothing>()
    object Loading: Resource<Nothing>()
    object Idle: Resource<Nothing>()
}
