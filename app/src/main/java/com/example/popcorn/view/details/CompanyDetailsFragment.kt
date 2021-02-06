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

class CompanyDetailsFragment : Fragment() {
    private lateinit var companyVM: CompanyViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        companyVM = ViewModelProvider(requireActivity()).get(CompanyViewModel::class.java)
        val view =  inflater.inflate(R.layout.fragment_company_details, container, false)

        companyVM.currentCompany.observe(viewLifecycleOwner, {
            // name:
            view.tv_companyName.text = it.name

            // HQ:
            if (!it.headquarters.isNullOrEmpty())
            {
                view.tv_companyHQ.text = it.headquarters
                view.tv_companyHQ.visibility = View.VISIBLE
            }
            else view.tv_companyHQ.visibility = View.GONE

            // origin country
            if (!it.origin_country.isNullOrEmpty())
                view.tv_companyHQ.text = "${it.headquarters} (${it.origin_country})"

            // homepage:
            if (!it.homepage.isNullOrEmpty())
            {
                view.tv_homepage.text = it.homepage
                view.tv_homepage.visibility = View.VISIBLE
            }
            else view.tv_homepage.visibility = View.GONE

            // logo:
            if (!it.logo_path.isNullOrEmpty())
            {
                val url = "https://image.tmdb.org/t/p/w185${it.logo_path}"
                Glide.with(view.iv_companyLogo).load(url).override(500, 200).fitCenter().into(view.iv_companyLogo)
                view.iv_companyLogo.visibility = View.VISIBLE
            }
            else view.iv_companyLogo.visibility = View.GONE
        })
        return view
    }
}