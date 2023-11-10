package com.example.market

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//product_item.xml 레이아웃에 정의된 뷰들을 참조하여 각 제품의 정보를 화면에 표시한다.
class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.product_image)
    val titleView: TextView = view.findViewById(R.id.product_title)
    val priceView: TextView = view.findViewById(R.id.product_price)

    // HomeFragment에서 SellDetailFragment로 이동할 때
    fun bind(product: Product, context: Context, navController: NavController) {
        Glide.with(context).load(product.imageUrl).into(imageView)
        titleView.text = product.title

        itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("productTitle", product.title)
            }
            Log.d("ProductViewHolder", "상세보기 이동 성공###############################")
            navController.navigate(R.id.sellDetailFragment, bundle)
        }
    }

}
