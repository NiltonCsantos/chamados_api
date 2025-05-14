package com.api.chamados.config.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
public record ResponseDto<T>(
        @Schema(description = "Status da resposta")
        Integer status,
        @Schema(description = "Objeto de resposta genérico")
        T response
){
    public static class Builder<T> {
        private HttpStatus status;
        private HttpHeaders headers;
        private T response;

        public Builder<T> status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder<T> headers(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        public Builder<T> response(T content) {
            this.response = content;
            return this;
        }

        public ResponseEntity<ResponseDto<T>> build() {
            assert (status != null);
            return ResponseEntity.status(status).headers(headers).body(new ResponseDto<>(status.value(), response));
        }
    }
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }
}
