package edu.iipw.pap.db.types;

import java.time.LocalDate;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class LocalDateStringType extends AbstractSingleColumnStandardBasicType<LocalDate> {
    public static final LocalDateStringType INSTANCE = new LocalDateStringType();

    public LocalDateStringType() {
        super(VarcharTypeDescriptor.INSTANCE, LocalDateStringJavaDescriptor.INSTANCE);
    }

    @Override public String getName() {
        return "LocalDateString";
    }
}
