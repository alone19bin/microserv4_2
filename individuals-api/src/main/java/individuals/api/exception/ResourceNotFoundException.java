package individuals.api.exception;


import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resourceName, String identifier) {
        super(
            String.format("%s не найден с идентификатором %s", resourceName, identifier), 
            HttpStatus.NOT_FOUND, 
            "RESOURCE_NOT_FOUND"
        );
    }
}