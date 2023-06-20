package com.serdjuk.wallpapers.model

data class JsonCategoryListModel(
    val collections: List<Collection?>? = null,
    val page: Int? = null,
    val per_page: Int? = null,
    val total_results: Int? = null
) {
    data class Collection(
        val description: String? = null,
        val id: String? = null,
        val media_count: Int? = null,
        val photos_count: Int? = null,
        val `private`: Boolean? = null,
        val title: String? = null,
        val videos_count: Int? = null
    )
}