package com.stiivenortiz.pruebatecnicaredeban.view.core.navigation.types

import android.net.Uri
import android.os.Build
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentInput
import kotlinx.serialization.json.Json

val paymentInputType = object : NavType<PaymentInput>(isNullableAllowed = true) {
    override fun put(
        bundle: SavedState, key: String, value: PaymentInput
    ) {
        bundle.putParcelable(key, value)
    }

    override fun get(
        bundle: SavedState, key: String
    ): PaymentInput? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getParcelable(key, PaymentInput::class.java)
        else
            bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PaymentInput {
        return Json.decodeFromString<PaymentInput>(value)
    }

    override fun serializeAsValue(value: PaymentInput): String {
        return Uri.encode(Json.encodeToString(value))
    }

}