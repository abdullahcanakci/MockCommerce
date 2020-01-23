package com.mockcommerce.modules.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mockcommerce.R
import com.mockcommerce.models.CategoryModel
import kotlinx.android.synthetic.main.fragment_categories.view.*
import kotlinx.android.synthetic.main.item_category.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : Fragment() {

    val args: CategoriesFragmentArgs by navArgs()
    private val viewModel by viewModel<CategoriesViewModel>()

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var requestId: Int? = null
        val id = args.categoryId

        if( id != 0 ){
            requestId = id
        }

        val adapter = CategoryItemAdapter{selectId, isCategory ->
            if(isCategory){
                val action = CategoriesFragmentDirections.actionCategoriesSelf(selectId.toInt())
                findNavController().navigate(action)
            } else {
                val action = CategoriesFragmentDirections.actionCategoriesToProductList(selectId.toInt())
                this.findNavController().navigate(action)
            }
        }
        view.category_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.category_list.adapter = adapter

        viewModel.getCategoryList(requestId).observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })



    }
}
