package com.vieja.myapplication.models

import java.util.*

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