package com.example.market

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//ProductAdapter의 역할은 RecyclerView에 어떤 데이터가 어떻게 표시될지를 결정

class ProductAdapter(
    private val context: Context,
    private val productList: List<Product>,
    private val navController: NavController
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    //productList라는 제품 리스트를 받아와서 이를 RecyclerView에 표시한다. 리스트의 각 아이템(제품)은 ProductViewHolder를 통해 표시된다.
    //onBindViewHolder 메소드에서는 ProductViewHolder에 제품의 이미지, 제목, 가격을 설정하여 화면에 표시한다.
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        Glide.with(context).load(product.imageUrl).into(holder.imageView)
        holder.titleView.text = product.title
        holder.priceView.text = product.price.toString()
        holder.bind(product, context, navController)
    }

    override fun getItemCount() = productList.size
}
