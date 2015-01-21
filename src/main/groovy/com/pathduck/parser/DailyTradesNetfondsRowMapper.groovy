package com.pathduck.parser

import java.time.LocalDateTime

class DailyTradesNetfondsRowMapper extends RowMapper {

    def static bindings = [time: LocalDateTime, price: BigDecimal, quantity: Integer]

    @Override
    def static map(List rows) {
        bindData(rows, bindings)
    }
}
