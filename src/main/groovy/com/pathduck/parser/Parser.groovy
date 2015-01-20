package com.pathduck.parser

import groovy.io.FileType
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

class Parser {

    private static final Logger logger = LogManager.getLogger(Parser.class);

    public static void main(String[] args) {
        processDirectory("${System.getProperty('user.home')}/dev/stock_data/statoil")
    }

    def static processDirectory(directory) {
        List fileList = findFileNamesIn(directory)
        fileList.each { File file ->
            parseFile(file, new DailyTradesNetfondsRowMapper())
        }
    }

    static def findFileNamesIn(String directory) {
        logger.debug("Fetching files in $directory")
        def list = []
        def dir = new File(directory)
        dir.eachFileRecurse(FileType.FILES) { file ->
            logger.debug("Found file: ${file.name}")
            list << file
        }
        list
    }

    static def parseFile(File file) {
        List rows = []
        file.eachLine {
            rows << it.split(',')
        }
    }

    static def parseFile(File file, RowMapper mapper) {
        List rows = parseFile(file)
        if (!mapper) return rows
        mapper.map(rows)
    }

}
