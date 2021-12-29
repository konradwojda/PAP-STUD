package edu.iipw.pap.db.types;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.BasicJavaDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

public class BooleanIntegerJavaDescriptor
        extends AbstractTypeDescriptor<Boolean>
        implements BasicJavaDescriptor<Boolean> {
    public static final BooleanIntegerJavaDescriptor INSTANCE = new BooleanIntegerJavaDescriptor();

    public BooleanIntegerJavaDescriptor() {
        super(Boolean.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public Boolean fromString(String string) {
        return Boolean.parseBoolean(string);
    }

    @Override
    public <X> X unwrap(Boolean value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (Integer.class.isAssignableFrom(type)) {
            return (X)Integer.valueOf(value.booleanValue() ? 1 : 0);
        }

        throw unknownUnwrap(type);
    }

    @Override
    public <X> Boolean wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (Integer.class.isInstance(value)) {
            return ((Integer)value).intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
        }

        throw unknownWrap(value.getClass());
    }
}
