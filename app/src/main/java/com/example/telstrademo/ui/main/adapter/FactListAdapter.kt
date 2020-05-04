package com.example.telstrademo.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.telstrademo.R
import com.example.telstrademo.data.model.FactDetailItem
import kotlinx.android.synthetic.main.item_layout.view.*

/*This class is used to set data received from api to the recyclerview */
class FactListAdapter(private val factsItem: ArrayList<FactDetailItem>) :
    RecyclerView.Adapter<FactListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        /*Attaching data on each row of holder*/
        fun bind(factDetail: FactDetailItem) {
            itemView.apply {
                factDetail.title?.let {
                    textViewTitle?.text = it
                } ?: run { textViewTitle?.text = context.getString(R.string.title_not_available) }

                factDetail.description?.let {
                    textViewDescription?.text = it
                } ?: run {
                    textViewDescription?.text = context.getString(R.string.desc_not_available)
                }

                //Glide used to download image and set to imageview
                Glide.with(imageViewListIcon.context)
                    .applyDefaultRequestOptions(object : RequestOptions() {}
                        .error(R.drawable.no_image_available))
                    .load(factDetail.imageHref)
                    .into(imageViewListIcon)
            }
        }
    }

    /* Initializing inflated view */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            ), parent.context
        )
    }

    override fun getItemCount(): Int = factsItem.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(factsItem[position])
    }

    /** function used to refresh data of recyclerview
     * @param factItem list items to be added
     */

    fun updateListData(factItem: List<FactDetailItem>) {
        this.factsItem.apply {
            clear()
            addAll(factItem)
        }

    }
}