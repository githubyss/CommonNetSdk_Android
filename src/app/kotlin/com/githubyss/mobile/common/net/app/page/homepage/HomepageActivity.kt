package com.githubyss.mobile.common.net.app.page.homepage

import com.githubyss.mobile.common.kit.base.view_binding.page.inline.BaseInlineBindingToolbarActivity
import com.githubyss.mobile.common.net.R


/**
 * HomepageActivity
 *
 * @author Ace Yan
 * @github githubyss
 * @createdTime 2021/12/16 11:39:34
 */
class HomepageActivity : BaseInlineBindingToolbarActivity() {

    /** ****************************** Properties ****************************** */

    companion object {
        private val TAG: String = HomepageActivity::class.java.simpleName
    }


    /** ****************************** Override ****************************** */

    override fun init() {
        switchFragment(HomepageFragment(), HomepageFragment.TAG, false, binding.layoutFragmentContainer.id)
    }

    override fun setToolbarTitle() {
        setToolbarTitle(R.string.comnet_homepage_title)
    }
}
