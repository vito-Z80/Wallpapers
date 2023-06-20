package com.serdjuk.wallpapers.model

data class JsonImageListModel(
    val page: Int? = null,
    val per_page: Int? = null,
    val photos: List<Photo?>? = null,
    val prev_page: String? = null,
    val total_results: Int? = null
) {
    data class Photo(
        val alt: String? = null,
        val avg_color: String? = null,
        val height: Int? = null,
        val id: Int? = null,
        val liked: Boolean? = null,
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
}