package com.mockcommerce.modules.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import com.mockcommerce.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_categories.view.*
import org.koin.android.ext.android.get
import timber.log.Timber

class CategoriesFragment : BaseFragment() {

    val args: CategoriesFragmentArgs by navArgs()

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

        val adapter = CategoryItemAdapter { selectId, isCategory ->
            if (isCategory) {
                val action = CategoriesFragmentDirections.actionCategoriesSelf(selectId)
                findNavController().navigate(action)
            } else {
                val action = CategoriesFragmentDirections.actionCategoriesToProductList(selectId)
                this.findNavController().navigate(action)
            }
        }
        view.category_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.category_list.adapter = adapter

        val appRepository = get<AppRepository>()

        val disposable = appRepository
            .getCategory()
            .subscribe({ result -> adapter.update(result) }, { error -> Timber.d("$error") })

        addToDisposable(disposable)
    }
}
