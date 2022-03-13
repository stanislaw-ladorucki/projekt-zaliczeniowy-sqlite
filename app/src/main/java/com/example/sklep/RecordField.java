package com.example.sklep;

import android.util.Pair;

public class RecordField extends Pair<String, DatabaseRecord.RecordFieldType> {
    public RecordField(String key, DatabaseRecord.RecordFieldType type) {
        super(key, type);
    }
}
