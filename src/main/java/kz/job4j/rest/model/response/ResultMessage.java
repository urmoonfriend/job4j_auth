package kz.job4j.rest.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultMessage<T> {
    private boolean success;
    private String error;
    private T message;

    public static <T> ResultMessage<T> success(T message) {
        return new ResultMessage<T>()
                .setMessage(message)
                .setSuccess(true);
    }

    public static <T> ResultMessage<T> success() {
        return new ResultMessage<T>()
                .setSuccess(true);
    }

    public static <T> ResultMessage<T> error(String error) {
        return new ResultMessage<T>()
                .setError(error)
                .setSuccess(false);
    }

}
