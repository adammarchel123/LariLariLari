package com.example.runningappyt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runningappyt.R
import com.example.runningappyt.db.Run
import com.example.runningappyt.other.TrackingUtility
import kotlinx.android.synthetic.main.item_run.view.*
import java.text.SimpleDateFormat
import java.util.*

// RecyclerView menampilkan sebuah set data yang berjumlah besar (ratusan — atau mungkin sampai jutaan)
// Recycler View adalah salah satu komponen pada Android Studio yang memungkinkan aplikasi mampu menampung ribuan bahkan ratusan ribu data tanpa mengalami lag atau hang pada aplikasi
// RecyclerView lebih cocok digunakan untuk menampung data yang banyak dan selalu berubah-ubah.
class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // DiffUtil memudahkan kita dalam mengupdate RecyclerView kita berdasarkan perubahan pada dataset yang hendak kita tampilkan
    // tanpa harus menggunakan method notifyDataSetChanged
    // Meningkatkan performa RecyclerView menggunakan DiffUtil
    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
                // Layout Inflater digunakan jika kita ingin membuat sebuah sub activity
                // fungsinya hampir sama seperti fragment, yaitu bagian kecil dari Activity
                // Layout Inflater ini berfungsi untuk membuat tampilan baru di atas tampilan layout utama
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_run,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {
            // Glide adalah salah satu library Android yang berguna untuk menampilkan gambar dari internet/URL.
            Glide.with(this).load(run.img).into(ivRunImage)

            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timeInMillis
            }
            // SimpleDateFormat untuk menampilkan format tanggal dalam Java
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${run.distanceInMeters / 1000f}km"
            tvDistance.text = distanceInKm

            tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned = "${run.caloriesburned}kcal"
            tvCalories.text = caloriesBurned
        }
    }
}