package edu.iipw.pap.db.types;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.IntegerTypeDescriptor;

public class BooleanIntegerType extends AbstractSingleColumnStandardBasicType<Boolean> {
    public static final BooleanIntegerType INSTANCE = new BooleanIntegerType();

    public BooleanIntegerType() {
        super(IntegerTypeDescriptor.INSTANCE, BooleanIntegerJavaDescriptor.INSTANCE);
    }

    @Override public String getName() {
        return "BooleanInteger";
    }
}
