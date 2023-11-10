package com.example.market

data class Product(val imageUrl: String, val title: String, val price: Int) {
    // Firestore에서 사용할 수 있도록 빈 생성자를 추가
    constructor() : this("", "", 0)
}
