package com.example.data

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.net.NetworkInterface
import java.security.MessageDigest
import java.util.Collections
import java.util.UUID

data class DeviceAddressInfo(
  val formattedAddress: String,
  val sourceDescription: String,
)

object DeviceAddressProvider {
  private const val DUMMY_MAC = "02:00:00:00:00:00"

  /**
   * Detects the device's hardware MAC if exposed by the OS, or safely derives a
   * consistent colon-separated CP Device Address from Android's privacy-safe SSAID
   * (Settings.Secure.ANDROID_ID) or a persistent app-scoped UUID fallback.
   */
  @SuppressLint("HardwareIds")
  fun detectDeviceAddress(context: Context, persistedFallbackUuid: String? = null): DeviceAddressInfo {
    val hardwareMac = readNonDummyInterfaceMac()
    if (hardwareMac != null) {
      return DeviceAddressInfo(
        formattedAddress = hardwareMac,
        sourceDescription = "Detected Network Address",
      )
    }

    val androidId = runCatching {
      Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }.getOrNull()?.trim().orEmpty()

    if (androidId.isNotEmpty() && androidId != "9774d56d682e549c") {
      return DeviceAddressInfo(
        formattedAddress = hashToMacFormat("android-ssaid:$androidId"),
        sourceDescription = "Android Device Address (SSAID)",
      )
    }

    val fallback = persistedFallbackUuid?.takeIf { it.isNotBlank() } ?: UUID.randomUUID().toString()
    return DeviceAddressInfo(
      formattedAddress = hashToMacFormat("app-uuid:$fallback"),
      sourceDescription = "App Device Address",
    )
  }

  private fun readNonDummyInterfaceMac(): String? {
    return runCatching {
      val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
      val preferred = interfaces.sortedByDescending { it.name.startsWith("wlan", ignoreCase = true) }
      for (nif in preferred) {
        if (nif.isLoopback) continue
        val bytes = nif.hardwareAddress ?: continue
        if (bytes.size != 6) continue
        val formatted = bytes.joinToString(":") { "%02X".format(it) }
        if (formatted != DUMMY_MAC && formatted != "00:00:00:00:00:00") {
          return@runCatching formatted
        }
      }
      null
    }.getOrNull()
  }

  internal fun hashToMacFormat(seed: String): String {
    val digest = MessageDigest.getInstance("SHA-256").digest(seed.toByteArray(Charsets.UTF_8))
    return (0 until 6).joinToString(":") { idx -> "%02X".format(digest[idx]) }
  }
}
