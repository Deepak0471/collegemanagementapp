package com.deepak.collegemanagementapp

import android.content.Context
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.squareup.picasso.Picasso

class MyAdapter(val context: Context, private val postvalues: ArrayList<postvalues>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        private const val TAG = "PRODUCT_TAG"
        //view types

        private const val VIEW_TYPE_CONTENT = 1
        private const val VIEW_TYPE_AD  = 2
        private const val VIEW_TYPE_TWO  = VIEW_TYPE_AD+ VIEW_TYPE_CONTENT

    }

    inner class Holderproduct(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val title: TextView = itemview.findViewById(R.id.txt_title)
        val description: TextView = itemview.findViewById(R.id.txt_description)
        val date: TextView = itemview.findViewById(R.id.txt_date)
        val url: TextView = itemview.findViewById(R.id.txt_url)
        val sino: TextView = itemview.findViewById(R.id.sino)

        //imageupload
        val item_image : ImageView = itemView.findViewById(R.id.rv_image)


    }


     inner class HolderNativead(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val ad_app_icon: ImageView = itemview.findViewById(R.id.ad_app_icon)
        val ad_headline: TextView = itemview.findViewById(R.id.ad_headline)
        val ad_advertiser: TextView = itemview.findViewById(R.id.ad_advertiser)
        val ad_stars: RatingBar = itemview.findViewById(R.id.ad_stars)
        val ad_body: TextView = itemview.findViewById(R.id.ad_body)
        val media_view: MediaView = itemview.findViewById(R.id.media_view)
        val ad_price: TextView = itemview.findViewById(R.id.ad_price)
        val ad_store: TextView = itemview.findViewById(R.id.ad_store)
        val ad_call_to_action: Button = itemview.findViewById(R.id.ad_call_to_action)
        val nativeAdView : NativeAdView = itemview.findViewById(R.id.nativeAdView)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View

        if (viewType == VIEW_TYPE_CONTENT) {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.cradview, parent, false)
            return Holderproduct(itemView)

        } else {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.row_native_ad, parent, false)
            return HolderNativead(itemView)

        }



    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == VIEW_TYPE_CONTENT) {
            val model = postvalues[position]
            val title = model.title
            val description = model.description
            val date = model.date
            val url = model.url
            val sino = model.sino
            val url1 = model.url1





            //getting holderproduct instance and adding it to ui

            val holderproduct = holder as Holderproduct
            holderproduct.title.text = title
            holderproduct.description.text = description
            holderproduct.date.text = date
            holderproduct.url.text = url
            holderproduct.sino.setText(""+sino)


            //image upload
//            Glide.with(context).load(postvalues[position].url1).into(holderproduct.item_image)
            Picasso.get().load(url1).into(holderproduct.item_image)
//            with(context).load(ImageURL).into(imageView);



            holder.itemView.setOnClickListener {
                Toast.makeText(context, "$title", Toast.LENGTH_SHORT).show()
            }



        } else if (getItemViewType(position) == VIEW_TYPE_AD) {


            val adloader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd { nativeAd ->
                    Log.d(TAG, "on-native ad loaded")
                    //adding ui elements and displaying ad

                    val holderNativead = holder as HolderNativead
                    displayNativeAd(holderNativead, nativeAd)
                }.withAdListener(object : AdListener() {

                    override fun onAdClicked() {
                        super.onAdClicked()
                    }

                    override fun onAdClosed() {
                        super.onAdClosed()
                    }

                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        super.onAdFailedToLoad(p0)
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                    }

                    override fun onAdOpened() {
                        super.onAdOpened()
                    }
                }).withNativeAdOptions(NativeAdOptions.Builder().build()).build()

            adloader.loadAd(AdRequest.Builder().build())


        }


    }

    private fun displayNativeAd(holderNativead: MyAdapter.HolderNativead, nativead: NativeAd) {

        // ####### get add assestsfrom nativead object ########

        val headline = nativead.headline
        val body = nativead.body
        val callToAction = nativead.callToAction
        val icon = nativead.icon
        val price = nativead.price
        val store = nativead.store
        val starRating = nativead.starRating
        val advertiser = nativead.advertiser
        val mediaContent = nativead.mediaContent


        // there is no guarentee that these ads load, so we need to check and reload ads

        if (headline == null) {
            // no content, hide view
            holderNativead.ad_headline.visibility = View.INVISIBLE

        } else {
            //have content,show view
            holderNativead.ad_headline.visibility = View.VISIBLE
            //set data to ui
            holderNativead.ad_headline.text = headline


        }


        if (body == null) {
            // no content, hide view
            holderNativead.ad_body.visibility = View.INVISIBLE

        } else {
            //have content,show view
            holderNativead.ad_body.visibility = View.VISIBLE
            //set data to ui
            holderNativead.ad_body.text = headline


        }

        if (icon == null) {
            // no content, hide view
            holderNativead.ad_app_icon.visibility = View.INVISIBLE

        } else {
            //have content,show view
            holderNativead.ad_app_icon.visibility = View.VISIBLE
            //set data to ui
            holderNativead.ad_app_icon.setImageDrawable(icon.drawable)


        }

        if (starRating == null) {
            // no content, hide view
            holderNativead.ad_stars.visibility = View.INVISIBLE

        } else {
            //have content,show view
            holderNativead.ad_stars.visibility = View.VISIBLE
            //set data to ui
            holderNativead.ad_stars.rating = starRating.toFloat()


        }

        if (price == null) {
            // no content, hide view
            holderNativead.ad_price.visibility = View.INVISIBLE

        } else {
            //have content,show view
            holderNativead.ad_price.visibility = View.VISIBLE
            //set data to ui
            holderNativead.ad_price.text = price


        }

        if (store == null) {
            // no content, hide view
            holderNativead.ad_store.visibility = View.INVISIBLE

        } else {
            //have content,show view
            holderNativead.ad_store.visibility = View.VISIBLE
            //set data to ui
            holderNativead.ad_store.text = store


        }

        if (advertiser == null) {
            // no content, hide view
            holderNativead.ad_advertiser.visibility = View.INVISIBLE

        } else {
            //have content,show view
            holderNativead.ad_advertiser.visibility = View.VISIBLE
            //set data to ui
            holderNativead.ad_advertiser.text = advertiser


        }

        if (mediaContent == null) {
            // no content, hide view
            holderNativead.media_view.visibility = View.INVISIBLE

        } else {
            //have content,show view
            holderNativead.media_view.visibility = View.VISIBLE
            //set data to ui
            holderNativead.media_view.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            holderNativead.media_view.setMediaContent(mediaContent)

        }

        if (callToAction == null) {
            // no content, hide view
            holderNativead.ad_call_to_action.visibility = View.INVISIBLE

        } else {
            //have content,show view
            holderNativead.ad_call_to_action.visibility = View.VISIBLE
            //set data to ui
            holderNativead.ad_call_to_action.text = callToAction
            // handle ad button click
            holderNativead.nativeAdView.callToActionView= holderNativead.ad_call_to_action



        }


        //add nativead the nativeadview
        holderNativead.nativeAdView.setNativeAd(nativead)



    }


    override fun getItemCount(): Int {
        return postvalues.size
    }

    override fun getItemViewType(position: Int): Int {

            if (position%5 == 0){
//        after 5  items show add
//                val x= getItemId(5)
//                val y = x + VIEW_TYPE_AD
            return VIEW_TYPE_AD

            } else{
//            // COnTENT SHOWER
            return VIEW_TYPE_CONTENT
        }





    }
}