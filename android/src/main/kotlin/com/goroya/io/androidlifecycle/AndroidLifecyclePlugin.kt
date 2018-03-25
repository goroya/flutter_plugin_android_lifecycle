package com.goroya.io.androidlifecycle

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.PluginRegistry.Registrar



class AndroidLifecyclePlugin(val registrar: Registrar) : MethodCallHandler, EventChannel.StreamHandler, PluginRegistry.NewIntentListener {
    companion object {
        private val TAG: String = AndroidLifecyclePlugin::class.java.simpleName
        private val ANDROID_LIFECYCLE_CHANNEL_NAME = "plugins.goroya.io/android_lifecycle"
        @JvmStatic
        fun registerWith(registrar: Registrar): Unit {
            val channel = MethodChannel(registrar.messenger(), "android_lifecycle")
            val plugin = AndroidLifecyclePlugin(registrar)
            registrar.addNewIntentListener(plugin)
            channel.setMethodCallHandler(plugin)

            val androidLifeCycleChannel = EventChannel(registrar.messenger(), ANDROID_LIFECYCLE_CHANNEL_NAME)
            androidLifeCycleChannel.setStreamHandler(plugin)
        }
    }
    private var _events: EventChannel.EventSink? = null
    init {
        registrar.activity().application.registerActivityLifecycleCallbacks(
                object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityPaused(activity: Activity?) {
                        Log.d(TAG, "onActivityPaused")

                        val eventParam = hashMapOf<String, String>()
                        eventParam["event"] = "onActivityPaused"
                        this@AndroidLifecyclePlugin._events?.success(eventParam)
                    }

                    override fun onActivityResumed(activity: Activity?) {
                        Log.d(TAG, "onActivityResumed")

                        val eventParam = hashMapOf<String, String>()
                        eventParam["event"] = "onActivityResumed"
                        this@AndroidLifecyclePlugin._events?.success(eventParam)
                    }

                    override fun onActivityStarted(activity: Activity?) {
                        Log.d(TAG, "onActivityStarted")

                        val eventParam = hashMapOf<String, String>()
                        eventParam["event"] = "onActivityStarted"
                        this@AndroidLifecyclePlugin._events?.success(eventParam)
                    }

                    override fun onActivityDestroyed(activity: Activity?) {
                        Log.d(TAG, "onActivityDestroyed")

                        val eventParam = hashMapOf<String, String>()
                        eventParam["event"] = "onActivityDestroyed"
                        this@AndroidLifecyclePlugin._events?.success(eventParam)
                    }

                    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                        Log.d(TAG, "onActivitySaveInstanceState")

                        val eventParam = hashMapOf<String, String>()
                        eventParam["event"] = "onActivitySaveInstanceState"
                        this@AndroidLifecyclePlugin._events?.success(eventParam)
                    }

                    override fun onActivityStopped(activity: Activity?) {
                        Log.d(TAG, "onActivityStopped")

                        val eventParam = hashMapOf<String, String>()
                        eventParam["event"] = "onActivityStopped"
                        this@AndroidLifecyclePlugin._events?.success(eventParam)
                    }

                    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                        Log.d(TAG, "onActivityCreated")

                        val eventParam = hashMapOf<String, String>()
                        eventParam["event"] = "onActivityCreated"
                        this@AndroidLifecyclePlugin._events?.success(eventParam)
                    }
                }
        )
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else {
            result.notImplemented()
        }
    }

    override fun onNewIntent(intent: Intent?): Boolean {
        Log.d(TAG, "onNewIntent")

        val eventParam = hashMapOf<String, String>()
        eventParam["event"] = "onNewIntent"
        this._events?.success(eventParam)
        return true
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        this._events= events
    }

    override fun onCancel(arguments: Any?) {
        this._events= null
    }
}
