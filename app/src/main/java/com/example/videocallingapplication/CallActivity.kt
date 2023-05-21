package com.example.videocallingapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser


class CallActivity : AppCompatActivity() {

    lateinit var userIdEditText: EditText
    lateinit var heyUser: TextView
    lateinit var voiceCallBtn: ZegoSendCallInvitationButton
    lateinit var videoCallBtn: ZegoSendCallInvitationButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        userIdEditText = findViewById(R.id.user_id_tv)
        heyUser = findViewById(R.id.hey_user_tv)
        voiceCallBtn = findViewById(R.id.voice_call_btn)
        videoCallBtn = findViewById(R.id.video_call_btn)


        val userID = intent.getStringExtra("userID")
        heyUser.text = "Hey ${userID}"

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called when the text is being changed
                val targetUserID : String = userIdEditText.text.toString().trim()
                setVideoCall(targetUserID)
                setVoiceCall(targetUserID)
            }


            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text has been changed
            }
        }
        userIdEditText.addTextChangedListener(textWatcher)


    }

    fun setVideoCall(targetUserID: String) {
        videoCallBtn.setIsVideoCall(true)
        videoCallBtn.setResourceID("zego_uikit_call")
        videoCallBtn.setInvitees(listOf(ZegoUIKitUser(targetUserID)))

    }
    fun setVoiceCall(targetUserID : String){
        voiceCallBtn.setIsVideoCall(false)
        voiceCallBtn.setResourceID("zego_uikit_call")
        voiceCallBtn.setInvitees(listOf(ZegoUIKitUser(targetUserID)))

    }
}