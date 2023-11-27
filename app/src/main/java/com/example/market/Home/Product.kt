package com.example.market.Home

//상품 정보를 나타내는 데이터 클래스다. 각 상품은 이미지 URL, 제목, 가격을 가지고 있다.
//Firestore에서 데이터를 가져올 때 이 클래스의 형태로 변환하기위해 만들음

data class Product(var imageUrl: String, var title: String, var price: Int, var id: String,
                   var seller: String?,
                   var sell: Boolean,
                   var sellerEmail: String?,
                   var content: String?
) {
    // Firestore에서 사용할 수 있도록 빈 생성자를 추가
    constructor() : this("", "", 0, "", null, true, null, null)
}
