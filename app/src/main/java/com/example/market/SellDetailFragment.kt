package com.example.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class SellDetailFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sell_detail, container, false)

        //ProductViewHolder 에서 홈프래그먼트에서 셀디테일프레그먼트로 이동할때 제품의 제목을
        //넘겨주는 코드가 있다. 그걸 받아오는 거임
        val productTitle = arguments?.getString("productTitle")
        db = FirebaseFirestore.getInstance()

        if (productTitle != null) {
            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
            val priceTextView = view.findViewById<TextView>(R.id.priceTextView)
            val sellerTextView = view.findViewById<TextView>(R.id.sellerTextView)
            val isAvailableTextView = view.findViewById<TextView>(R.id.isAvailableTextView)

            db.collection("posts").document(productTitle)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val productDetail = document.toObject(ProductDetail::class.java)
                        if (productDetail != null) {
                            titleTextView.text = productDetail.title
                            contentTextView.text = productDetail.content
                            priceTextView.text = productDetail.price.toString()
                            sellerTextView.text = productDetail.seller
                            isAvailableTextView.text = if (productDetail.isAvailable) "판매중" else "판매완료"
                        }
                    }
                }
        }


        return view
    }
}
