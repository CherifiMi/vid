package com.example.merg3

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.coremedia.iso.boxes.Container
import com.googlecode.mp4parser.FileDataSourceImpl
import com.googlecode.mp4parser.authoring.Movie
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator
import com.googlecode.mp4parser.authoring.tracks.MP3TrackImpl
import java.io.File
import java.io.FileOutputStream
import java.nio.channels.FileChannel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        merge(
            videoPath = File(this.cacheDir, "vid.mp4").path,
            audioPath = File(this.cacheDir, "aud.mp3").path,
            outputPath = File(this.cacheDir,"out2.mkv").path,
        )

    }
}

private fun merge(videoPath: String, audioPath: String, outputPath: String) {
    //Create a movie object from the video file
    val video: Movie = MovieCreator.build(videoPath)

    //Create a movie object from the audio file
    val audio: Movie = MovieCreator.build(audioPath)

    //Append the audio track to the video movie
    video.addTrack(audio.getTracks().get(0))

    //Create a container for the output file
    val out: Container = DefaultMp4Builder().build(video)

    //Write the output to a new file
    val fc: FileChannel = FileOutputStream(File(outputPath)).getChannel()
    out.writeContainer(fc)
    fc.close()
}
