package com.example.videocallingapplication

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService


class HomeActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    lateinit var userIdEditTextView: TextView
    lateinit var startBtn : Button
    lateinit var displayname : String
    lateinit var signOut : Button
    private lateinit var loadingProgressBar: ProgressBar

    private lateinit var googleSignInClient : GoogleSignInClient
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        displayname = intent.getStringExtra("name").toString()
        findViewById<TextView>(R.id.textView3).text = "Hey $displayname"
        userIdEditTextView = findViewById(R.id.user_id_et)
        startBtn = findViewById(R.id.startBtn)
        signOut = findViewById(R.id.signOutBtn)

        signOut.setOnClickListener {
        auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        }

        startBtn.setOnClickListener {
            val userID = userIdEditTextView.text.toString().trim()
            if(userID.isEmpty()){
                Toast.makeText(this, "Please provide the user Id", Toast.LENGTH_SHORT).show()
            }
            else{
                // start the service
                loadingProgressBar.visibility = View.VISIBLE

                startService(userID)
                // Perform the button click action
                performButtonClickAction()
                val intent = Intent(this, CallActivity::class.java)
                intent.putExtra("userID", userID)
                startActivity(intent)

            }
        }


    }

    fun startService(userID : String){

        val application: Application = application
        val appID: Long = 1821780757 // Your AppID
        val appSign: String = "4814b423bfdc864df7a2cc2830c2b2d5ce79d8297776150bf2261a7c4a02e522"// Your AppSign
        val userName: String = displayname // Your UserName

        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig().apply {
            this.notifyWhenAppRunningInBackgroundOrQuit = true
        }


        val notificationConfig = ZegoNotificationConfig().apply {
            sound = "zego_uikit_sound_call"
            channelID = "CallInvitation"
            channelName = "CallInvitation"
        }

        ZegoUIKitPrebuiltCallInvitationService.init(application, appID, appSign, userID, userName, callInvitationConfig)

    }

    override fun onDestroy() {
        super.onDestroy()
        ZegoUIKitPrebuiltCallInvitationService.unInit()
    }

    private fun performButtonClickAction() {
        // Simulate a delay to show the loading effect
        Handler().postDelayed({
            // Hide the loadingProgressBar after the action is completed
            loadingProgressBar.visibility = View.GONE

            // Perform the desired action here
            // This is where you can navigate to the next screen or perform other operations
        }, 2000) // Adjust the delay time as needed
    }
}