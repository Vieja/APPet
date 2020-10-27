package com.vieja.appet.models

import android.content.Context
import com.vieja.appet.R
import java.util.*
import kotlin.collections.ArrayList

class Pet(var id: Int,
          var name: String?,
          var category: String,
          var breed: String?,
          var species: String?,
          var sex: String?,
          var image: ByteArray?,
          var birth: Date?,
          var death: Date?,
          var color: String?,
          var description: String?,
          var acquisition_date: Date?,
          var genes: String?) {

    fun getLocalPetSex(c : Context) : String {
        when (sex) {
            "male" -> {
                return c.getString(R.string.sex_male)
            }
            "female" -> {
                return c.getString(R.string.sex_female)
            }
            "unknown" -> {
                return c.getString(R.string.sex_unknown)
            }
        }
        return ""
    }

    fun getNullAttributesNames() : ArrayList<String> {
        lateinit var array : ArrayList<String>
        if (name == null) array.add("name")
        if (breed == null) array.add("breed")
        if (species == null) array.add("species")
        if (sex == null) array.add("sex")
        if (birth == null) array.add("birth")
        if (death == null) array.add("death")
        if (color == null) array.add("color")
        if (description == null) array.add("description")
        if (acquisition_date == null) array.add("acquisition_date")
        if (genes == null) array.add("genes")
        return array
    }
}

