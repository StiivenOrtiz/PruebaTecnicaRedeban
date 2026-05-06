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

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "status_code")
    val statusCode: String,

    @ColumnInfo(name = "status_description")
    val statusDescription: String? = null,

    @ColumnInfo(name = "hex_data")
    val hexData: String? = null,

    @ColumnInfo(name = "masked_card_number")
    val maskedCardNumber: String? = null,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long

)