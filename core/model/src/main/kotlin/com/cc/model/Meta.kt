package com.cc.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("last_updated_at")
    val lastlyUpdated: String
)
