package com.martafode.lib.ui.utils

import com.google.android.material.tabs.TabLayout

fun TabLayout.forEachTab(block: (tab: TabLayout.Tab) -> Unit) {
    for (index in 0 until tabCount) {
        val tab = this.getTabAt(index) ?: error("The tab should not be null.")
        block(tab)
    }
}

fun TabLayout.setTabsEnabled(enabled: Boolean) = forEachTab { it.view.isEnabled = enabled }

class OnTabSelectedOnlyListener(private val block: (tab: TabLayout.Tab) -> Unit) : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) = block(tab)
    override fun onTabReselected(tab: TabLayout.Tab) {}
    override fun onTabUnselected(tab: TabLayout.Tab) {}
}
