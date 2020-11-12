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
}

