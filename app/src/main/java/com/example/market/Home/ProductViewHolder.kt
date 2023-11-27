package com.example.market.Home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market.R

//product_item.xml 레이아웃에 정의된 뷰들을 참조하여 각 제품의 정보를 화면에 표시한다.
//ProductAdapter가 사용하는 ViewHolder다. 각 아이템의 뷰를 참조하고, 데이터를 화면에 표시하는 역할
//또한 아이템 클릭 시 상세보기 화면으로 이동하는 동작을 정의
class ProductViewHolder(view: View, private val fromProfile: Boolean) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.product_image)
    val titleView: TextView = view.findViewById(R.id.product_title)
    val priceView: TextView = view.findViewById(R.id.product_price)
    val statusView : TextView = view.findViewById(R.id.product_status)
    // HomeFragment에서 SellDetailFragment로 이동할 때
    fun bind(product: Product, context: Context, navController: NavController) {
        Glide.with(context).load(product.imageUrl).error(R.drawable.ic_default_image).into(imageView)
        titleView.text = product.title
        itemView.setOnClickListener {
            val bundle = Bundle().apply {
                Log.d("productview", product.id)
                putString("productId", product.id)
            }
            if (fromProfile) {
                //이 로그는 Fragment로 잘 이동하는지 확인해보기위해 로그에 찍어보는것.
                Log.d("ProductViewHolder", "수정하기 이동 성공###############################")
                navController.navigate(R.id.editFragment, bundle)
            }
            else {
                //이 로그는 SellDetailFragment로 잘 이동하는지 확인해보기위해 로그에 찍어보는것.
                Log.d("ProductViewHolder", "상세보기 이동 성공###############################")
                navController.navigate(R.id.sellDetailFragment, bundle)
            }
        }
    }

}