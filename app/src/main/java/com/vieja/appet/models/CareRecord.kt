package com.vieja.appet.models

import java.sql.Time
import java.util.*

class CareRecord (var id: Int,
                  var title: String,
                  var subtitle: String?,
                  var note: String?,
                  var date: Date?,
                  var hour: Time?,
                  var image: ByteArray?,
                  var category: String) {

}