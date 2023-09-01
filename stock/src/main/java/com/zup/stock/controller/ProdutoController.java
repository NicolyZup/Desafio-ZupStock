package com.zup.stock.controller;

import com.zup.stock.model.dto.ProdutoAllDTO;
import com.zup.stock.model.dto.ProdutoPutDTO;
import com.zup.stock.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/ZupStock/produtos")
@Validated
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "\uD83D\uDCE6 - Listar todos os Produtos", description = "Retorna uma lista de todos os produtos disponíveis.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso!"),
            @ApiResponse(responseCode = "200", description = "Nenhum produto cadastrado ainda"),
            @ApiResponse(responseCode = "404", description = "Lista não localizada.")
    })
    @GetMapping
    public ResponseEntity<?> listarProdutos(){
        if(produtoService.listar().size() == 0){
            return ResponseEntity.ok().body("Nenhum produto cadastrado ainda.");
        } else {
            return ResponseEntity.ok(produtoService.listar());
        }
    }

    @Operation(summary = "\uD83D\uDCE6 - Listar produto por id ", description = "Retorna um produto por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro - tipo de id inválido"),
            @ApiResponse(responseCode = "404", description = "Erro - id inexistente.")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<ProdutoAllDTO>> listarProdutoPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.listarPorId(id));
    }

    @Operation(summary = "\uD83D\uDCE6 - Cadastrar produto ", description = "Cadastra um produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro - sem parâmetro"),
            @ApiResponse(responseCode = "400", description = "Erro - campo vazio")
    })
    @PostMapping
    public ResponseEntity<ProdutoAllDTO> cadastrarProduto(@Valid @RequestBody ProdutoAllDTO produtoDTO){
        ProdutoAllDTO produtoNovo = produtoService.criar(produtoDTO);
        return new ResponseEntity<>(produtoNovo, HttpStatus.CREATED);
    }

    @Operation(summary = "\uD83D\uDCE6 - Editar produto", description = "Editar informação de produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto alterado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro - sem parâmetro"),
            @ApiResponse(responseCode = "400", description = "Erro - campo vazio")
    })
    @PutMapping(path = "/{id}")
    public ResponseEntity<ProdutoPutDTO> alterarProduto(@PathVariable Long id,@Valid @RequestBody ProdutoPutDTO produtoDTO){
        ProdutoPutDTO produtoNovo = produtoService.alterar(id, produtoDTO);
        return ResponseEntity.ok(produtoNovo);
    }


    @Operation(summary = "\uD83D\uDCE6 - Excluir um produto", description = "Excluir um produto", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro - tipo de id inválido"),
            @ApiResponse(responseCode = "404", description = "Erro - id inexistente.")
    })
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id){
        produtoService.excluir(id);
        return ResponseEntity.ok().body("Produto excluído com sucesso!");
    }
}
