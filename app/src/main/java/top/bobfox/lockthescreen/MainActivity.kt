package top.bobfox.lockscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

// below code is for using device admin to lock screen - but it requires pin number to unlock every time. pffft
//        val devicePolicyManager = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
//        if (!devicePolicyManager.isAdminActive(ComponentName(this, android.app.admin.DeviceAdminReceiver::class.java)))
//            Toast.makeText(this, "Enable in device admin settings first", Toast.LENGTH_LONG).show()
//
//        devicePolicyManager.lockNow()

        if (!isAccessibilityServiceEnabled())
            requireAccessibility()
        else
            startService(Intent(applicationContext, LockScreenAccessibilityService::class.java))

        finish()
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        return Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )?.contains(
            packageName + "/" + LockScreenAccessibilityService::class.java.name
        ) ?: false
    }

    private fun requireAccessibility() {
        startActivity(Intent(ACTION_ACCESSIBILITY_SETTINGS))
    }

}