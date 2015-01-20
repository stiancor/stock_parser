package com.pathduck.parser

import spock.lang.Specification

import java.time.LocalDateTime

class DailyTradesNetfondsRowMapperSpec extends Specification {

    def 'binds data correctly'() {
        given:
        def dataToBeBound = [['time', 'price', 'quantity', 'board', 'source', 'buyer', 'seller', 'initiator'],
                             ['20150120T090014', '132.9', '2500', '', 'Auto trade', 'CSB', 'BCSL']]

        when:
        def list = DailyTradesNetfondsRowMapper.map(dataToBeBound)
        def trade = list.first()

        then:
        trade['time'] == LocalDateTime.of(2015, 1, 20, 9, 0, 14)
        trade['price'] == new BigDecimal('132.9')
        trade['quantity'] == 2500
        trade['board'] == ''
        trade['source'] == 'Auto trade'
        trade['buyer'] == 'CSB'
        trade['seller'] == 'BCSL'
        trade['initiator'] == ''
    }

}
