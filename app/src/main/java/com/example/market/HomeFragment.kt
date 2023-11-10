package com.example.market

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class HomeFragment : Fragment() {
    lateinit var navController: NavController
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var adapter: ProductAdapter
    private lateinit var recyclerView : RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view) // 여기에 수정
        recyclerView.layoutManager = LinearLayoutManager(context)

        val filterButton = view.findViewById<Button>(R.id.filterButton)
        filterButton.setOnClickListener {
            // 필터링 옵션 Dialog 표시
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Filter")
                .setSingleChoiceItems(arrayOf("All", "Available", "Not Available"), -1) { dialog, which ->
                    when (which) {
                        0 -> loadProducts(null)
                        1 -> loadProducts(true)
                        2 -> loadProducts(false)
                    }
                    dialog.dismiss()
                }
                .create()
            dialog.show()
        }

        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        loadProducts(null) // onCreateView에서 이동
    }

    private fun loadProducts(isAvailable: Boolean?) {
        val productList = mutableListOf<Product>()
        adapter = ProductAdapter(requireContext(), productList, navController)
        recyclerView.adapter = adapter

        val products = if (isAvailable == null) {
            db.collection("posts")
        } else {
            db.collection("posts").whereEqualTo("isAvailable", isAvailable)
        }

        products.get().addOnSuccessListener { result ->
            for (document in result) {
                val product = document.toObject(Product::class.java)
                productList.add(product)
            }
            adapter.notifyDataSetChanged()
        }
    }
}
