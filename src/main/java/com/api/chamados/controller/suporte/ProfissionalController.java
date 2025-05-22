package com.api.chamados.controller.suporte;

import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.suporte.ProfissionalService;
import com.api.chamados.service.suporte.dto.ProfissionalDto;
import com.api.chamados.service.suporte.dto.ProfissionalMaisChamadoDto;
import com.api.chamados.service.suporte.form.AtualizarChamadoForm;
import com.api.chamados.service.suporte.form.ProfissionalFiltroForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/profissionais")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @GetMapping()
    public ResponseEntity<ResponseDto<Page<ProfissionalDto>>> listarProfissionais(@ParameterObject ProfissionalFiltroForm filtro,
                                                                                  @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        var profissionais = profissionalService.listarProfissionais(pageable, filtro);
        return ResponseDto.<Page<ProfissionalDto>>builder()
                .response(profissionais)
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("{proNrId}")
    public ResponseEntity<ResponseDto<ProfissionalDto>> buscarPorId(@PathVariable long proNrId) {
        var profissional = profissionalService.buscarProfissionalPorId(proNrId);
        return ResponseDto.<ProfissionalDto>builder()
                .status(HttpStatus.CREATED)
                .response(profissional)
                .build();
    }

    @GetMapping("chamados/{chaNrId}")
    public ResponseEntity<ResponseDto<ProfissionalDto>> buscarChamados(@PathVariable long chaNrId) {
        var profissional = profissionalService.buscarProfissionalEmUmChamado(chaNrId);
        return ResponseDto.<ProfissionalDto>builder()
                .status(HttpStatus.CREATED)
                .response(profissional)
                .build();
    }

    @PatchMapping("{proNrId}")
    public ResponseEntity<ResponseDto<Void>> ativarOuInativar(@PathVariable long proNrId) {
        profissionalService.ativarDesativarProfissional(proNrId);
        return ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("mais-chamados")
    public ResponseEntity<ResponseDto<Page<ProfissionalMaisChamadoDto>>> listarProfissionaisMaisChamados(
            @RequestParam(required = false) Long munNrId,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        var profissionais = profissionalService.listarProfissionaisMaisChamados(pageable, munNrId);
        return ResponseDto.<Page<ProfissionalMaisChamadoDto>>builder()
                .response(profissionais)
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("gerenciar-chamado")
    public ResponseEntity<ResponseDto<Void>> ativarOuInativar(@RequestBody @Valid AtualizarChamadoForm form) {
        profissionalService.atenderOuCancelarChamado(form);
        return ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }

}
