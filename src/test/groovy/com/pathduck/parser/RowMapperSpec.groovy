package com.pathduck.parser

import spock.lang.Specification

import java.time.LocalDateTime

class RowMapperSpec extends Specification {

    def 'No bindings binds everything to String'() {
        given:
        def bindings = [:]

        when:
        def value = RowMapper.bind('name', 'someData', bindings)

        then:
        value instanceof String
        value == 'someData'
    }

    def 'No matching bindings binds to String'() {
        given:
        def bindings = ['price': BigDecimal]

        when:
        def value = RowMapper.bind('name', 'someData', bindings)

        then:
        value instanceof String
        value == 'someData'
    }

    def 'Matching binding binds to BigDecimal'() {
        given:
        def bindings = ['price': BigDecimal]

        when:
        def value = RowMapper.bind('price', '2.33', bindings)

        then:
        value instanceof BigDecimal
        value == new BigDecimal('2.33')
    }

    def 'matching binding binds to LocalDateTime'() {
        given:
        def bindings = ['time': LocalDateTime]

        when:
        def value = RowMapper.bind('time', '20150120T090014', bindings)

        then:
        value instanceof LocalDateTime
        value == LocalDateTime.of(2015, 1, 20, 9, 0, 14)
    }

    def 'binding different types binds correctly'() {
        given:
        def bindings = ['price': BigDecimal, 'time': LocalDateTime, 'firstName': String]
        def dataToBeBound = [['price', 'firstName', 'lastName', 'time'],
                             ['123.45', 'Alice', 'Wonder', '20150120T090014'],
                             ['543.21', 'Bob', 'Land', '20150121T090014']]

        when:
        def list = RowMapper.bindData(dataToBeBound, bindings)

        then:
        list.size() == 2
        list[0] instanceof Map
        list[1] instanceof Map
        list[0].price == new BigDecimal('123.45')
        list[1].price == new BigDecimal('543.21')
        list[0].firstName == 'Alice'
        list[1].firstName == 'Bob'
        list[0].lastName == 'Wonder'
        list[1].lastName == 'Land'
        list[0].time == LocalDateTime.of(2015, 1, 20, 9, 0, 14)
        list[1].time == LocalDateTime.of(2015, 1, 21, 9, 0, 14)
    }

}
