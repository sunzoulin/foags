package com.sbl.foags.activity.authenticate.common.address


interface SelectAddressListener {

    fun onSelectAddress(province: String, city: String, county: String)
}