package com.kineticthinkers.financetick.billcategory.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.kineticthinkers.financetick.BaseActivity
import com.kineticthinkers.financetick.R
import com.kineticthinkers.financetick.billcategory.BillCategoryEditActivity
import com.kineticthinkers.financetick.model.BillCategory
import java.util.*

class BillCategoryListadapter(
    private var categoryList: ArrayList<BillCategory>,
    private var billCategoryListActivity: BaseActivity
) :
    RecyclerView.Adapter<BillCategoryListadapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var category_name_txt: TextView = view.findViewById(R.id.category_name_txt)
        var parent_category_txt: TextView = view.findViewById(R.id.parent_category_txt)
        //  var genre: TextView = view.findViewById(R.id.genre)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bill_cat, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            holder.category_name_txt.text = categoryList.get(position).CATEGORY_NAME
            holder.parent_category_txt.text =
                billCategoryListActivity.ParentCategories[categoryList.get(position).PARENT_CATEGORY.toInt()]
            holder.itemView.setOnClickListener(View.OnClickListener {
                val b = Bundle()
                b.putSerializable("BillCategory", categoryList.get(position))
                billCategoryListActivity.callActivity(BillCategoryEditActivity(), false, b)
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}