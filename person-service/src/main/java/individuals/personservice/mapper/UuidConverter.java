package individuals.personservice.mapper;

import org.hibernate.validator.constraints.UUID;

public class UuidConverter {
    public static UUID toHibernateUuid(UUID uuid) {
        return uuid; // Просто возвращаем UUID без изменений
    }

    public static UUID fromHibernateUuid(UUID hibernateUuid) {
        return hibernateUuid; // Просто возвращаем UUID без изменений
    }
}