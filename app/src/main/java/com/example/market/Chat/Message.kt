package com.example.market.Chat

data class Message( //판매자에게 메시지 보내는 데이터
    val purchase: String?,
    val message: String?,
    val seller: String?
)
