package com.redcodetechnologies.mlm.models

class VideosModal {
    private var videoId : String = ""
    private var title : String = ""
    private var duration : String = ""
    fun getVideoId():String {
        return videoId
    }
    fun setVideoId(videoId:String) {
        this.videoId = videoId
    }
    fun getTitle():String {
        return title
    }
    fun setTitle(title:String) {
        this.title = title
    }
    fun getDuration():String {
        return duration
    }
    fun setDuration(duration:String) {
        this.duration = duration
    }

public override fun toString():String {
    return ("YoutubeVideoModel{" +
            "videoId='" + videoId + '\''.toString() +
            ", title='" + title + '\''.toString() +
            ", duration='" + duration + '\''.toString() +
            '}'.toString())
}

}
