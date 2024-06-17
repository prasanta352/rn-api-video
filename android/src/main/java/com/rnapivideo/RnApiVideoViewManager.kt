package com.rnapivideo

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import video.api.player.ApiVideoPlayerController
import video.api.player.models.VideoOptions
import video.api.player.models.VideoType
import video.api.player.views.ApiVideoExoPlayerView

class RnApiVideoViewManager : SimpleViewManager<View>() {
  private var playerController: ApiVideoPlayerController? = null
  private var playerView: ApiVideoExoPlayerView? = null
  override fun getName() = "RnApiVideoView"
  private var reactContext: ThemedReactContext? = null

  override fun createViewInstance(reactContext: ThemedReactContext): View {
    val playerView = ApiVideoExoPlayerView(reactContext)
    val playerControllerListener = object : ApiVideoPlayerController.Listener {
      override fun onError(error: Exception) {
        Log.e("TAG", "An error happened", error)
      }

      override fun onReady() {
        println("Player is ready: $playerController")
      }
    }

    val playerController = ApiVideoPlayerController(
      reactContext.applicationContext,
      null, // For private video: VideoOptions("YOUR_VIDEO_ID", VideoType.VOD, "YOUR_PRIVATE_VIDEO_TOKEN")
      true,
      playerControllerListener,
      playerView
    )
    playerController.isLooping = true
    playerView.showControls = true
    playerView.viewFit = ApiVideoExoPlayerView.ViewFit.Contains
    this.playerController = playerController
    this.reactContext = reactContext
    this.playerView = playerView
    return playerView
  }

  override fun onDropViewInstance(view: View) {
    super.onDropViewInstance(view)
    println("onDropViewInstance")
    playerController?.pause()
    playerController?.release()
  }

  @ReactProp(name = "color")
  fun setColor(view: View, color: String) {
    view.setBackgroundColor(Color.parseColor(color))
  }


  @ReactProp(name = "videoId")
  fun setVideoId(view: View, videoId: String) {
    println("videoId: $videoId $playerController")


    playerController.let {
      if (it == null) {
        return
      }

      it.videoOptions = VideoOptions(videoId, VideoType.VOD)
      println("it.autoplay: ${it.autoplay} ${it.isPlaying}")
//
//      if (it.autoplay) {
//        it.play()
//
//      } else {
//        it.pause()
//      }
    }

  }

  @ReactProp(name = "autoplay")
  fun setAutoplay(view: View, autoplay: Boolean) {
    println("autoplay: $autoplay $playerController")
    playerController.let {
      if (it == null) {
        return
      }
      it.autoplay = autoplay

    }

  }
  @ReactProp(name = "isPlaying")
  fun setIsPlaying(view: View, isPlaying: Boolean) {
    println("isPlaying: $isPlaying $playerController")
    playerController.let {
      if (it == null) {
        return
      }
      if(isPlaying) {
        it.play()
      } else {
        it.pause()
      }
    }

  }


  @ReactProp(name = "isMuted")
  fun setIsMuted(view: View, isMuted: Boolean) {
    println("isMuted: $isMuted $playerController")
    println("isMuted: ${playerController?.isMuted}")

    playerController?.isMuted = true


  }



}
