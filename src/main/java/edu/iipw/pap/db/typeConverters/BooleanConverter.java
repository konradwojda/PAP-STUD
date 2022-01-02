package edu.iipw.pap.db.typeConverters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) return null;
        return Integer.valueOf(attribute.booleanValue() ? 1 : 0);
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        return Boolean.valueOf(dbData.equals(0) ? false : true);
    }

}
