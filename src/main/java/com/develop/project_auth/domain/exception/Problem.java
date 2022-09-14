package com.develop.project_auth.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
@Schema(name = "Problem")
public class Problem {

  @Schema(example = "400")
  private final Integer status;

  @Schema(example = "2019-12-01T18:09:02.70844Z")
  private final LocalDateTime timestamp;

  @Schema(example = "https://www.project-auth.com.br/dados-invalidos")
  private final String type;

  @Schema(example = "Dados inválidos")
  private final String title;

  @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
  private final String detail;

  @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
  private final String userMessage;

  @Schema(description = "Lista de objetos ou campos que geraram o erro (opcional)")
  private final List<Object> objects;


  @Getter
  @Builder
  @Schema(name = "ObjetoProblema")
  public static class Object {

    @Schema(example = "preço")
    private final String name;

    @Schema(example = "O preço é obrigatório")
    private final String userMessage;

  }
}
