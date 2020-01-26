package com.mayur.activity.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.util.StringUtils;

public class EntityAlreadyExists extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993856878594778164L;
	
	public EntityAlreadyExists(Class clazz, String...parameterList) {
		super(generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, parameterList)));
	}
	
    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) +
                " already exists "  +
                searchParams;
    }

    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid argument list");
        
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }

}
