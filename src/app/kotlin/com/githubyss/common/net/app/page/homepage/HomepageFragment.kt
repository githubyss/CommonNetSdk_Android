package com.githubyss.common.net.app.page.homepage

import android.view.View
import com.githubyss.common.kit.base.view_binding.page.inline.BaseInlineBindingToolbarFragment
import com.githubyss.common.kit.base.view_binding.page.inline.bindView
import com.githubyss.common.net.R
import com.githubyss.common.net.databinding.ComnetFragmentHomepageBinding


/**
 * HomepageFragment
 *
 * @author Ace Yan
 * @github githubyss
 * @createdTime 2021/12/16 11:39:42
 */
class HomepageFragment : BaseInlineBindingToolbarFragment(R.layout.comnet_fragment_homepage) {

    /** ****************************** Properties ****************************** */

    companion object {
        val TAG: String = HomepageFragment::class.java.simpleName
    }

    private val binding by bindView<ComnetFragmentHomepageBinding>()


    /** ****************************** Override ****************************** */

    override fun setupUi() {
        initView()
    }

    override fun setToolbarTitle() {
        setToolbarTitle(R.string.comnet_homepage_title)
    }


    /** ****************************** Functions ****************************** */

    private fun initView() {
        binding.btnVolley.setOnClickListener(onClickListener)
    }


    /** ****************************** Implementations ****************************** */

    private val onClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.btn_volley -> {
            }
        }
    }
}
