package ph.com.tteh.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ObjectUtils {
    private static final Logger log = LoggerFactory.getLogger(ObjectUtils.class);

    private ObjectUtils() {
        throw new IllegalStateException("Object Utility class");
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Copy properties from source to target, including subclass fields.
     */
    public static <T> T copyAs(Object source, Class<T> targetClass) {
        if (source == null) return null;

        try {
            T target = targetClass.getDeclaredConstructor().newInstance();

            List<Field> sourceFields = getAllFields(source.getClass());
            List<Field> targetFields = getAllFields(targetClass);

            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);

                Object value = sourceField.get(source);

                if (value == null) {
                    continue;
                }

                targetFields.stream()
                        .filter(f -> f.getName().equals(sourceField.getName()))
                        .findFirst()
                        .ifPresent(targetField -> {
                            try {
                                targetField.setAccessible(true);

                                Object convertedValue = value;

                                // Same type
                                if (!targetField.getType().isAssignableFrom(value.getClass())) {

                                    // Handle List
                                    if (value instanceof List<?> list) {
                                        convertedValue = list.stream()
                                                .map(item -> copyAs(item, getListGenericType(targetField)))
                                                .toList();

                                        // Handle nested object
                                    } else {
                                        convertedValue = copyAs(value, targetField.getType());
                                    }
                                }

                                targetField.set(target, convertedValue);

                            } catch (Exception e) {
                                log.error("Failed to copy field {}", targetField.getName(), e);
                            }
                        });
            }

            return target;

        } catch (Exception e) {
            log.error("Failed to copy object", e);
            return null;
        }
    }

    private static Class<?> getListGenericType(Field field) {
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    public static <T> List<T> copyListAs(List<?> sourceList, Class<T> targetClass) {
        if (sourceList == null) return Collections.emptyList();

        return sourceList.stream()
                .map(item -> copyAs(item, targetClass))
                .toList();
    }

    /**
     * Converts a JSON string into the specified object type.
     */
    public static <T> T fromJson(String json, Class<T> targetClass) {
        if (json == null || json.isBlank()) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(json, targetClass);
        } catch (Exception e) {
            log.error("Failed to deserialize JSON to {}", targetClass.getSimpleName(), e);
            return null;
        }
    }

    public static String fromObject(Object source) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(source);
        } catch (Exception e) {
            log.error("Failed to deserialize JSON to {}", source.getClass().getSimpleName(), e);
            return null;
        }
    }

    /** Get all fields of a class including inherited fields */
    private static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = Arrays.stream(type.getDeclaredFields()).collect(Collectors.toList());
        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
    }

    public static Date getLocalDateTime() {
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        log.info("Current Local Date and Time: {}", ldt);
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isEmpty(@Nullable Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).isEmpty();
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }
        return false;
    }

    public static String randomPasswordGenerator(int len) throws NoSuchAlgorithmException {
        String passwordStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = SecureRandom.getInstanceStrong();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(passwordStr.charAt(rand.nextInt(passwordStr.length())));
        }
        return sb.toString();
    }

    public static String randomOneTimePassword() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        return String.valueOf(100000 + random.nextInt(900000));
    }

    public static String replaceTemplate(String template, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            template = template.replace(placeholder, entry.getValue());
        }
        return template;
    }

    public static String toJsonString(Object obj){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, type, context) ->
                                new JsonPrimitive(src.toString()))
                .create();
        return gson.toJson(obj);
    }
}
