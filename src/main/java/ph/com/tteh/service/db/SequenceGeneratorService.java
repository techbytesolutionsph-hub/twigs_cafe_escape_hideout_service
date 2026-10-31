package ph.com.tteh.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ph.com.tteh.configuration.schema.SchemaPropertiesConfig;
import ph.com.tteh.exception.ServiceException;

@RequiredArgsConstructor
@Component
@Slf4j
public class SequenceGeneratorService {

    private final JdbcTemplate jdbcTemplate;
    private final SchemaPropertiesConfig schemaPropertiesConfig;

    /**
     * Get next value from a DB sequence
     *
     * @param sequenceName Name of the sequence (with schema)
     * @return next ID as long
     * @throws ServiceException if failed
     */
    public long getNextSequence(String sequenceName) throws ServiceException {
        try {
            String sql = String.format(
                    schemaPropertiesConfig.getQueryForObject(),
                    sequenceName
            );

            Long nextVal = jdbcTemplate.queryForObject(sql, Long.class);

            if (nextVal == null) {
                throw new ServiceException("Sequence " + sequenceName + " returned null!");
            }

            return nextVal;

        } catch (Exception e) {
            log.error("Failed to get next sequence value: {}", e.getMessage(), e);
            throw new ServiceException("Failed to get next identity for sequence: " + sequenceName);
        }
    }

    public long getStaffIdNextSequence() throws ServiceException {
        return getNextSequence(String.format("%s.app_user_id_seq", schemaPropertiesConfig.getSchema()));
    }
    
}