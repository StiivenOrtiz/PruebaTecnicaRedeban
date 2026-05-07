package com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    val id: Long = 0,

    @ColumnInfo(name = "receipt_id")
    val receiptId: String? = null,

    @ColumnInfo(name = "amount")
    val amount: String,

    @ColumnInfo(name = "internal_status")
    val internalStatus: String,

    @ColumnInfo(name = "business_status")
    val businessStatus: String,

    @ColumnInfo(name = "business_status_code")
    val businessStatusCode: String? = null,

    @ColumnInfo(name = "business_status_description")
    val businessStatusDescription: String? = null,

    @ColumnInfo(name = "pan_fingerprint")
    val panFingerprint: String? = null,

    @ColumnInfo(name = "masked_card_number")
    val maskedCardNumber: String? = null,

    @ColumnInfo(name = "operation_type")
    val operationType: String,

    @ColumnInfo(name = "parent_transaction_id")
    val parentTransactionId: Long? = null,

    @ColumnInfo(name = "is_voided")
    val isVoided: Boolean = false,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long

)