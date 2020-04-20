package com.github.stevep.youtube.data

data class YouTubeResponse(var kind: String,
                           var pageInfo: PageInfo,
                           var items: List<Item>)

data class PageInfo(var totalResults: Int,
                    var resultsPerPage: Int)

data class Item(var snippet: Snippet)

data class Snippet(var title: String,
                   var description: String,
                   var publishedAt: String,
                   var thumbnails: Map<String, Thumbnail>)

data class Thumbnail(var url: String,
                     var width:  Int,
                     var height: Int)

