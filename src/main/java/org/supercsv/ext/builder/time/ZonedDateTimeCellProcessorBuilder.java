package org.supercsv.ext.builder.time;

import java.lang.annotation.Annotation;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Optional;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.time.FmtZonedDateTime;
import org.supercsv.cellprocessor.time.ParseZonedDateTime;
import org.supercsv.ext.annotation.CsvDateConverter;
import org.supercsv.ext.exception.SuperCsvInvalidAnnotationException;

/**
 * The cell processor builder for {@link ZonedDateTime}.
 *
 * @author T.TSUCHIE
 *
 */
public class ZonedDateTimeCellProcessorBuilder extends AbstractTemporalAccessorCellProcessorBuilder<ZonedDateTime> {
    
    @Override
    protected String getDefaultPattern() {
        return "yyyy/MM/dd HH:mm:ss";
    }
    
    @Override
    protected ZonedDateTime parseTemporal(final String value, final DateTimeFormatter formatter) {
        return ZonedDateTime.parse(value, formatter);
    }
    
    @Override
    public ZonedDateTime getParseValue(final Class<ZonedDateTime> type, final Annotation[] annos, final String strValue) {
        
        final Optional<CsvDateConverter> converterAnno = getAnnotation(annos);
        
        final String pattern = getPattern(converterAnno);
        final ResolverStyle style = getResolverStyle(converterAnno);
        final Locale locale = getLocale(converterAnno);
        final ZoneId zone = getZoneId(converterAnno);
        final DateTimeFormatter formatter = createDateTimeFormatter(pattern, style, locale, zone);
        
        try {
            return ZonedDateTime.parse(strValue, formatter);
            
        } catch(DateTimeParseException e) {
            throw new SuperCsvInvalidAnnotationException(
                    String.format("default '%s' value cannot parse to Date with pattern '%s'",
                            strValue, pattern), e);
            
        }
    }
    
    @Override
    public CellProcessor buildOutputCellProcessor(final Class<ZonedDateTime> type, final Annotation[] annos,
            final CellProcessor processor, final boolean ignoreValidationProcessor) {
        
        final Optional<CsvDateConverter> converterAnno = getAnnotation(annos);
        final String pattern = getPattern(converterAnno);
        final ResolverStyle style = getResolverStyle(converterAnno);
        final Locale locale = getLocale(converterAnno);
        final ZoneId zone = getZoneId(converterAnno);
        
        final DateTimeFormatter formatter = createDateTimeFormatter(pattern, style, locale, zone);
        
        final Optional<ZonedDateTime> min = getMin(converterAnno).map(s -> parseTemporal(s, formatter));
        final Optional<ZonedDateTime> max = getMax(converterAnno).map(s -> parseTemporal(s, formatter));
        
        CellProcessor cp = processor;
        cp = (cp == null ? new FmtZonedDateTime(formatter) : new FmtZonedDateTime(formatter, cp));
        
        if(!ignoreValidationProcessor) {
            cp = prependRangeProcessor(min, max, cp);
        }
        
        return cp;
    }
    
    @Override
    public CellProcessor buildInputCellProcessor(final Class<ZonedDateTime> type, final Annotation[] annos,
                final CellProcessor processor) {
        
        final Optional<CsvDateConverter> converterAnno = getAnnotation(annos);
        final String pattern = getPattern(converterAnno);
        final ResolverStyle style = getResolverStyle(converterAnno);
        final Locale locale = getLocale(converterAnno);
        final ZoneId zone = getZoneId(converterAnno);
        
        final DateTimeFormatter formatter = createDateTimeFormatter(pattern, style, locale, zone);
        
        CellProcessor cp = processor;
        cp = (cp == null ? new ParseZonedDateTime(formatter) : new ParseZonedDateTime(formatter, cp));
        
        return cp;
        
    }
    
    
}