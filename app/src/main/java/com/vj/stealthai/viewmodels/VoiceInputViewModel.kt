package com.vj.stealthai.viewmodels


import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vj.stealthai.R
import com.vj.stealthai.services.GeminiService
import io.livekit.android.ConnectOptions
import io.livekit.android.LiveKit
import io.livekit.android.annotations.Beta
import io.livekit.android.events.RoomEvent
import io.livekit.android.events.collect
import io.livekit.android.room.Room
import io.livekit.android.room.track.LocalAudioTrack
import io.livekit.android.room.types.TranscriptionSegment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

/*class VoiceInputViewModel(application: Application) : AndroidViewModel(application) {
    var userInput by mutableStateOf("")
        private set
    var aiResponse by mutableStateOf("Press mic to speak")
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isRecording by mutableStateOf(false)
        private set

    private var mediaRecorder: MediaRecorder? = null
    private var speechRecognizer: SpeechRecognizer? = null
    private val geminiModel by lazy {
        GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = application.applicationContext.resources.getString(R.string.google_api_key)
        )
    }

    fun startRecording(context: Context) {
        try {
            // Reset states
            errorMessage = null
            isRecording = true
            aiResponse = "Listening... speak now"

            // Initialize speech recognizer
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onResults(results: Bundle) {
                        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        matches?.firstOrNull()?.let { recognizedText ->
                            userInput = recognizedText
                            sendToGemini(recognizedText)
                        }
                    }

                    override fun onError(error: Int) {
                        errorMessage = when (error) {
                            SpeechRecognizer.ERROR_NO_MATCH -> "No speech recognized"
                            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech detected"
                            else -> "Speech recognition error: $error"
                        }
                        isRecording = false
                    }

                    // Other required overrides
                    override fun onReadyForSpeech(params: Bundle) {}
                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray) {}
                    override fun onEndOfSpeech() {}
                    override fun onPartialResults(partialResults: Bundle) {}
                    override fun onEvent(eventType: Int, params: Bundle) {}
                })
            }

            // Start listening directly (no MediaRecorder needed)
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 2000)
                putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 2000)
            }
            speechRecognizer?.startListening(intent)

        } catch (e: Exception) {
            errorMessage = "Recording failed: ${e.localizedMessage}"
            isRecording = false
        }
    }

    fun stopRecording() {
        try {
            speechRecognizer?.stopListening()
        } catch (e: Exception) {
            errorMessage = "Error stopping recording: ${e.localizedMessage}"
        }
        isRecording = false
    }

    private fun sendToGemini(text: String) {
        isLoading = true
        aiResponse = "Processing your request..."
        viewModelScope.launch {
            try {
                val response = geminiModel.generateContent(text)
                aiResponse = response.text ?: "No response from Gemini"
            } catch (e: Exception) {
                errorMessage = "Error from Gemini: ${e.localizedMessage}"
                aiResponse = "Sorry, I couldn't process that request"
            } finally {
                isLoading = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizer?.destroy()
    }
}*/
/*class VoiceInputViewModel(application: Application) : AndroidViewModel(application) {
    var userInput by mutableStateOf("")
        private set
    var aiResponse by mutableStateOf("Speak or type your message and I'll respond!")
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isRecording by mutableStateOf(false)
        private set

    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    private val geminiService = GeminiService(application.applicationContext.resources.getString(R.string.google_api_key))

    fun updateUserInput(input: String) {
        userInput = input
        errorMessage = null
    }

    fun startRecording(context: Context) {
        try {
            audioFile = File.createTempFile("audio_${System.currentTimeMillis()}", ".wav", context.cacheDir)
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile?.absolutePath)
                prepare()
                start()
                isRecording = true
            }
        } catch (e: Exception) {
            errorMessage = "Recording failed: ${e.localizedMessage}"
            isRecording = false
        }
    }

    fun stopRecording() {
        try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            isRecording = false
        } catch (e: Exception) {
            errorMessage = "Stop recording failed: ${e.localizedMessage}"
        } finally {
            mediaRecorder = null
        }
    }

    fun getAIResponse() {
        if (userInput.isBlank()) {
            errorMessage = "Please enter or say something first"
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                aiResponse = geminiService.generateResponse(userInput)
            } catch (e: Exception) {
                errorMessage = "Error: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    // For direct text input without voice
    fun sendTextInput() {
        if (userInput.isNotBlank()) {
            getAIResponse()
        }
    }
}*/


/*class VoiceInputViewModel(application: Application) : AndroidViewModel(application) {
    var userInput by mutableStateOf("")
        private set
    var aiResponse by mutableStateOf("Speak or type your message and I'll respond!")
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isRecording by mutableStateOf(false)
        private set

    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    private val geminiService = GeminiService(application.resources.getString(R.string.google_api_key))

    fun updateUserInput(input: String) {
        userInput = input
        errorMessage = null
    }

    fun startRecording(context: Context) {
        try {
            audioFile = File.createTempFile("audio_${System.currentTimeMillis()}", ".m4a", context.cacheDir)
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile?.absolutePath)
                prepare()
                start()
            }
            isRecording = true
        } catch (e: Exception) {
            errorMessage = "Recording failed: ${e.localizedMessage}"
            isRecording = false
        }
    }

    fun stopRecordingAndTranscribe() {
        try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
            isRecording = false

            transcribeAudio()
        } catch (e: Exception) {
            errorMessage = "Stop failed: ${e.localizedMessage}"
        }
    }

    private fun transcribeAudio() {
        audioFile?.let { file ->
            viewModelScope.launch {
                isLoading = true
                try {
                    val text = geminiService.transcribeAudio(file)
                    userInput = text
                } catch (e: Exception) {
                    errorMessage = "Transcription failed: ${e.localizedMessage}"
                } finally {
                    isLoading = false
                }
            }
        } ?: run {
            errorMessage = "No audio file to transcribe"
        }
    }

    fun getAIResponse() {
        if (userInput.isBlank()) {
            errorMessage = "Please enter or say something first"
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                aiResponse = geminiService.generateResponse(userInput)
            } catch (e: Exception) {
                errorMessage = "Error: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    fun sendTextInput() {
        getAIResponse()
    }
}*/


@OptIn(Beta::class)
class VoiceInputViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var room: Room
    private lateinit var micTrack: LocalAudioTrack

    var isConnectedToRoom : Boolean = false

    var userInput by mutableStateOf("")
        private set
    var aiResponse by mutableStateOf("Speak or type your message and I'll respond!")
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isRecording by mutableStateOf(false)
        private set

    var setOfVoiceText = mutableSetOf<String>()

    private val geminiService = GeminiService(application.getString(R.string.google_api_key))
    private var speechRecognizer: SpeechRecognizer? = null

    fun updateUserInput(input: String) {
        userInput = input
        errorMessage = null
    }

    init {
        viewModelScope.launch {
            launch {
                room = LiveKit.create(application.applicationContext)
                room.events.collect { event ->
                    Log.e("eventName : ", event.toString())
                    when (event) {
                        is RoomEvent.Connected -> {
                            Log.e("Roomname : ", room.name.toString())
                            isConnectedToRoom = true
                        }
                        is RoomEvent.TrackSubscribed -> onTrackSubscribed(event)
                        is RoomEvent.Disconnected -> {
                            isConnectedToRoom = false
                            isRecording = false
                        }
                        is RoomEvent.DataReceived -> {
                            val transcript = event.data.toString(Charsets.UTF_8)
                            Log.e("Roomname : ", transcript)
                        }
                        is RoomEvent.TranscriptionReceived -> {
                            //val transcript = event.transcriptionSegments
                            val finalSegments = event.transcriptionSegments
                                .filter { it.final }
                                .sortedBy { it.firstReceivedTime }
                            appendTranscriptStreaming(finalSegments)
                           // Log.e("Roomname : ", transcript.to())
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }

    fun startSpeechRecognition(context: Context) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            errorMessage = "Speech recognition not supported on this device"
            return
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        /*val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
        }
*/
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US")
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        }
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                isRecording = false
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                matches?.firstOrNull()?.let {
                    updateUserInput(it)
                }
            }

            override fun onError(error: Int) {
                isRecording = false
                errorMessage = "Speech recognition error: $error"
            }

            override fun onBeginningOfSpeech() { isRecording = true }
            override fun onEndOfSpeech() { isRecording = false }
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer?.startListening(intent)
        isRecording = true
    }

    fun stopSpeechRecognition() {
        speechRecognizer?.stopListening()
        //speechRecognizer?.destroy()
        isRecording = false
    }

    fun getAIResponse() {
        if (userInput.isBlank()) {
            errorMessage = "Please enter or say something first"
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                aiResponse = geminiService.generateResponse(userInput)
                showStreamingResponse(aiResponse)
                //showWordStreamingResponse(aiResponse)
            } catch (e: Exception) {
                errorMessage = "Error: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun showStreamingResponse(fullText: String) {
        aiResponse = "" // Clear previous output
        viewModelScope.launch {
            for (i in 1..fullText.length) {
                aiResponse = fullText.substring(0, i)
                delay(5L) // Delay between characters (adjust for speed)
            }
        }
    }

    private fun appendTranscriptStreaming(newText: List<TranscriptionSegment>) {
        viewModelScope.launch {
            for (segment in newText) {
                Log.e("Roomname segment id :", segment.id + " text : " + segment.text)
                if(!setOfVoiceText.contains(segment.id)) {
                    setOfVoiceText.add(segment.id)
                    userInput += segment.text
                    delay(10L)
                    Log.e("Roomname segment id :", segment.id + " text : " + segment.text)
                }
            }
        }
    }
    /*fun showWordStreamingResponse(fullText: String) {
        aiResponse = ""
        viewModelScope.launch {
            fullText.split(" ").forEach { word ->
                aiResponse += "$word "
                delay(150L)
            }
        }
    }*/

    fun sendTextInput() {
        if (userInput.isNotBlank()) {
            getAIResponse()
            userInput = ""
        }
    }

    fun connectToRoom(context: Context) {
        if(isConnectedToRoom) {
            room.disconnect()
            return
        }
        isConnectedToRoom = true
        viewModelScope.launch {

            // Setup event handling.
            /*launch {
                room.events.collect { event ->
                    when (event) {
                        is RoomEvent.Connected -> {
                            Log.e("Roomname : ", room.name.toString())
                            isConnectedToRoom = true
                        }
                        is RoomEvent.TrackSubscribed -> onTrackSubscribed(event)
                        is RoomEvent.Disconnected -> isConnectedToRoom = false
                        is RoomEvent.DataReceived -> {
                            val transcript = event.data.toString(Charsets.UTF_8)
                        }
                        else -> {}
                    }
                }
            }*/

            // Connect to server.
            room.connect(
                "wss://stealth-mode-ai-sa9vk4du.livekit.cloud",
                context.resources.getString(R.string.livekit_token),
                ConnectOptions(autoSubscribe = true)
            )
            isRecording = true


            // Publish audio/video to the room
            val localParticipant = room.localParticipant
            localParticipant.setMicrophoneEnabled(true)
            localParticipant.setCameraEnabled(false)
        }
    }

    private fun onTrackSubscribed(event: RoomEvent.TrackSubscribed) {
        Log.e("log event ", "onTrackSubscribed" + event.track.name)
//        val track = event.track
//
//
//        room.registerTextStreamHandler("", TextStreamHandler())
    }

    override fun onCleared() {
        super.onCleared()
        if(isConnectedToRoom) {
            room.disconnect()
        }
    }
}