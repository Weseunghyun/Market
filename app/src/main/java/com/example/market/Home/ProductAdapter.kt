package com.example.market.Home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market.R


//ProductAdapter의 역할은 RecyclerView에 표시될 데이터 리스트를 관리하고
//어떻게 표시될지를 결정
class ProductAdapter(
    // 현재 화면의 Context. 이를 통해 현재 화면의 정보를 얻을 수 있다.
    private val context: Context,
    private var productList: List<Product>, // 화면에 표시할 상품 리스트
    private val navController: NavController, // 네비게이션을 제어하는 컨트롤러
    private val fromProfile: Boolean
)   // RecyclerView.Adapter를 상속받는다. 이를 통해 RecyclerView에 표시할 아이템들을 제어한다.
    : RecyclerView.Adapter<ProductViewHolder>() {

    // 아이템 뷰가 필요할 때 호출되는 메서드. 새로운 ViewHolder를 생성하고 반환한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view, fromProfile)
    }

    //productList라는 제품 리스트를 받아와서 이를 RecyclerView에 표시한다. 리스트의 각 아이템(제품)은 ProductViewHolder를 통해 표시된다.
    //onBindViewHolder 메소드에서는 ProductViewHolder에 제품의 이미지, 제목, 가격을 설정하여 화면에 표시한다.
    //즉 ViewHolder에 데이터를 바인딩할 때 호출되는 메소드라고 생각하면 된다.
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        // 표시할 데이터를 가져온다.
        val product = productList[position]
        // Glide 라이브러리를 사용하여 이미지를 로드하고, 이를 ImageView에 표시.
        Glide.with(context).load(product.imageUrl).error(R.drawable.ic_default_image).into(holder.imageView)
        // 제품의 제목을 TextView에 설정한다.
        holder.titleView.text = product.title
        // 제품의 가격을 TextView에 설정
        holder.priceView.text = product.price.toString()
        holder.statusView.text = if (product.sell == true) "판매중" else "판매완료"
        // ViewHolder에 데이터를 바인딩.
        // 이 메서드 내에서는 아이템 클릭 시 상세보기 화면으로 이동하는 동작을 정의합니다.
        holder.bind(product, context, navController)
    }

    override fun getItemCount() = productList.size

    fun setData(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
        Log.d("ProductAdapter", "productList: $productList")
    }
}