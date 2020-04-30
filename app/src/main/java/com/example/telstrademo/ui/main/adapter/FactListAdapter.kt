package com.example.telstrademo.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.telstrademo.R
import com.example.telstrademo.data.model.Rows
import kotlinx.android.synthetic.main.item_layout.view.*

/*This class is used to set date received from api to the recyclerview */
class FactListAdapter(private val factsList: ArrayList<Rows?>) :
    RecyclerView.Adapter<FactListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /*Attaching data on each row of holder*/
        fun bind(factDetail: Rows) {
            itemView.apply {

                textViewTitle?.text = factDetail.title
                textViewDescription?.text = factDetail.description
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = factsList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        factsList[position]?.let { holder.bind(it) }
    }

    /** function used to refresh data of recyclerview
     * @param factList list items to be added
     */

    fun updateListData(factList: List<Rows>) {
        this.factsList.apply {
            clear()
            addAll(factList)
        }

    }
}