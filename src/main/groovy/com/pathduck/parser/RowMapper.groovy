package com.pathduck.parser

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class RowMapper {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")

    def abstract map(List rows)

    static def bindData(List rows, Map bindings) {
        def names = rows.first()
        rows = rows.subList(1, rows.size())
        rows.collect { r ->
            def m = [:]
            names.eachWithIndex { it, i ->
                m[it] = i < r.size() ? bind(it, r[i], bindings) : ''
            }
            m
        }
    }

    static def bind(String name, String data, bindings) {
        if (!bindings[name] || bindings[name] == String)
            return data
        if (bindings[name] == LocalDateTime)
            return LocalDateTime.parse(data, formatter)
        if (bindings[name] == BigDecimal)
            return new BigDecimal(data)
        if (bindings[name] == Integer)
            return data as Integer
    }
}
