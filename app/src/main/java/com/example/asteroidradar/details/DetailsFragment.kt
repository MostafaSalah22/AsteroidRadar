package com.example.asteroidradar.details

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )

        setData()



        binding.helpImage.setOnClickListener {
            openDialog()
        }

        return binding.root
    }


    private fun setData() {
        val detailsFragmentArgs by navArgs<DetailsFragmentArgs>()

        binding.asteroid = detailsFragmentArgs.asteroid

            when(detailsFragmentArgs.asteroid.isPotentiallyHazardous){
            true -> binding.topImage.setImageResource(R.drawable.asteroid_hazardous)
            else -> binding.topImage.setImageResource(R.drawable.asteroid_safe)
        }

        binding.dateTextView.text = detailsFragmentArgs.asteroid.closeApproachDate
        binding.absoluteTextView.text = detailsFragmentArgs.asteroid.absoluteMagnitude.toString() + " au"
        binding.estimatedTextView.text = detailsFragmentArgs.asteroid.estimatedDiameter.toString()+ " km"
        binding.velocityTextView.text = detailsFragmentArgs.asteroid.relativeVelocity.toString()+ " km/s"
        binding.distanceTextView.text = detailsFragmentArgs.asteroid.distanceFromEarth.toString()+ " au"
    }

    private fun openDialog(){
        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.dialog_message)
        builder.setPositiveButton("Accept") { _, _ ->
            return@setPositiveButton
        }
        val dialog = builder.create()
        dialog.show()
    }
}