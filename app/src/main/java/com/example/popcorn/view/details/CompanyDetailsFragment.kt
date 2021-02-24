package com.example.popcorn.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.viewmodel.CompanyViewModel
import kotlinx.android.synthetic.main.fragment_company_details.view.*

// Fragment which is displayed after clicking company's name in movie's / tv show's details:
class CompanyDetailsFragment : Fragment() {
    // ViewModel:
    private lateinit var companyVM : CompanyViewModel


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ViewModel:
        companyVM = ViewModelProvider(requireActivity()).get(CompanyViewModel::class.java)

        // Declaration of view:
        val view =  inflater.inflate(R.layout.fragment_company_details, container, false)

        // Updating fragment after receiving response from API:
        companyVM.currentCompany.observe(viewLifecycleOwner, {
            // Name:
            view.tv_companyName.text = it.name

            // HeadQuarters:
            if (!it.headquarters.isNullOrEmpty())
            {
                view.tv_companyHQ.text = it.headquarters
                view.tv_companyHQ.visibility = View.VISIBLE
            }
            else view.tv_companyHQ.visibility = View.GONE

            // Origin country:
            if (!it.origin_country.isNullOrEmpty())
                view.tv_companyHQ.text = "${it.headquarters} (${it.origin_country})"

            // Homepage:
            if (!it.homepage.isNullOrEmpty())
            {
                view.tv_homepage.text = it.homepage
                view.tv_homepage.visibility = View.VISIBLE
            }
            else view.tv_homepage.visibility = View.GONE

            // Logo:
            if (!it.logo_path.isNullOrEmpty())
            {
                val url = "https://image.tmdb.org/t/p/w185${it.logo_path}"
                Glide.with(view.iv_companyLogo).load(url).override(500, 200).fitCenter().into(view.iv_companyLogo) // resizing
                view.iv_companyLogo.visibility = View.VISIBLE
            }
            else view.iv_companyLogo.visibility = View.GONE
        })

        return view
    }
}