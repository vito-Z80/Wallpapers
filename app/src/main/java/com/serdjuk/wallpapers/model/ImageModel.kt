package com.serdjuk.wallpapers.model

data class ImageModel(
    val alt: String? = null,
    val height: Int? = null,
    val id: Int? = null,
    val liked: Boolean = false,
    val photographer: String? = null,
    val photographer_id: Int? = null,
    val photographer_url: String? = null,
    val src: Src? = null,
    val url: String? = null,
    val width: Int? = null
) {
    data class Src(
        val landscape: String? = null,
        val large: String? = null,
        val large2x: String? = null,
        val medium: String? = null,
        val original: String? = null,
        val portrait: String? = null,
        val small: String? = null,
        val tiny: String? = null
    )
}