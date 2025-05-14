package com.api.chamados.controller.viacep;


import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.viacep.ViaCepService;
import com.api.chamados.service.viacep.dto.ConsultaCepDto;
import com.api.chamados.service.viacep.dto.ViaCepDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ViaCepController {

    private final ViaCepService viaCepService;

    @GetMapping("/cep/{cep}")
    @Operation(summary = "Buscar endereços através do cep", description = "Endpoint responsável por buscar endereços através do cep.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ViaCepDto.class)))
    public ResponseEntity<ResponseDto<ConsultaCepDto>> buscarUnidadeAtendimentoPorId(@PathVariable String cep) {
        var consultaCepDto = viaCepService.buscarCepEcadastrarEnderecoNaBase(cep);
        return ResponseDto.<ConsultaCepDto>builder()
                .status(HttpStatus.OK)
                .response(consultaCepDto)
                .build();
    }
}
