package org.sang.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.sang.utils.HasDefaultInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiping on 2019/11/15.
 */
public class GenericEnumTypeHandler<T extends HasDefaultInterface> extends BaseTypeHandler<T> {
    private Map<Integer, T> valueMap = Collections.emptyMap();
    private Class<T> type;
    private T unknown;
    private static final Logger logger = LoggerFactory.getLogger(GenericEnumTypeHandler.class);

    public GenericEnumTypeHandler(Class<T> type) throws IllegalAccessException, InstantiationException {
        if(type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            T[] ts = type.getEnumConstants();
            if(null == ts) {
                throw new IllegalArgumentException("Type argument cannot be non enum class");
            } else {
                this.valueMap = new HashMap(ts.length << 1);
                T[] arr$ = ts;
                int len$ = ts.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    T t = arr$[i$];
                    this.valueMap.put(t.getValue(), t);
                    if(null == this.unknown) {
                        this.unknown = (T)t.getDefault();
                    }
                }

                this.type = type;
            }
        }
    }

    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue().intValue());
    }

    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        return rs.wasNull()?null:this.locateEnumStatus(i);
    }

    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        return rs.wasNull()?null:this.locateEnumStatus(i);
    }

    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        return cs.wasNull()?null:this.locateEnumStatus(i);
    }

    private T locateEnumStatus(int code) {
        T t = this.valueMap.get(Integer.valueOf(code));
        if(t != null) {
            return t;
        } else {
            if(this.unknown == null) {
                logger.warn("value:{} not convertable by EnumClass:{}", Integer.valueOf(code), this.type.getCanonicalName());
            }

            return this.unknown;
        }
    }
}
