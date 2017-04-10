package edu.umdearborn.astronomyapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;

@Service
public class CsvParserServiceImpl implements CsvParserService {

	private static final Logger logger = LoggerFactory.getLogger(CsvParserServiceImpl.class);
	private static final Map<MimeType, CSVFormat> FORMAT = ImmutableMap.of(
			// Non-M$ format
			MimeType.valueOf("text/csv"),
			CSVFormat.RFC4180.withHeader(CsvParserService.Header.class).withIgnoreHeaderCase(),
			// M$ format
			MimeType.valueOf("application/vnd.ms-excel"),
			CSVFormat.EXCEL.withHeader(CsvParserService.Header.class).withIgnoreHeaderCase());

	@Override
	public List<String[]> parseClassList(InputStream stream, MimeType type) {
		try (CSVParser parser = FORMAT
				.getOrDefault(type, CSVFormat.RFC4180.withHeader(CsvParserService.Header.class).withIgnoreHeaderCase())
				.parse(new InputStreamReader(stream))) {
			return parser.getRecords().parallelStream().map(e -> {
				String[] record = new String[] { StringUtils.trimToEmpty(e.get(CsvParserService.Header.EMAIL)),
						StringUtils.trimToEmpty(e.get(CsvParserService.Header.FIRST_NAME)),
						StringUtils.trimToEmpty(e.get(CsvParserService.Header.LAST_NAME)) };

				if (!EmailValidator.getInstance().isValid(record[0]) || StringUtils.isEmpty(record[1])
						|| StringUtils.isEmpty(record[2])) {
					logger.warn("Record number {} with value: '{}' is not valid, skipping", e.getRecordNumber(),
							Arrays.toString(record));
					return null;
				}

				return record;
			}).filter(Predicates.notNull()).collect(Collectors.toList());
		} catch (IOException ioe) {
			logger.error("Error parsing CSV", ioe);
			throw new RuntimeException(ioe);
		}

	}

}
