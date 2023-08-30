package com.zup.stock.controller;


import com.zup.stock.model.dto.AtributosParciaisUpdate;
import com.zup.stock.model.dto.ProdutoAllDTO;
import com.zup.stock.model.dto.ProdutoPutDTO;
import com.zup.stock.service.ProdutoService;
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

    @GetMapping
    public ResponseEntity<?> listarProdutos(){
        if(produtoService.listar().size() == 0){
            return ResponseEntity.ok().body("Nenhum produto cadastrado ainda.");
        } else {
            return ResponseEntity.ok(produtoService.listar());
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<ProdutoAllDTO>> listarProdutoPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.listarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoAllDTO> cadastrarProduto(@Valid @RequestBody ProdutoAllDTO produtoDTO){
        ProdutoAllDTO produtoNovo = produtoService.criar(produtoDTO);
        return new ResponseEntity<>(produtoNovo, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProdutoPutDTO> alterarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoPutDTO produtoDTO){
        ProdutoPutDTO produtoNovo = produtoService.alterar(id, produtoDTO);
        return ResponseEntity.ok(produtoNovo);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id){
        produtoService.excluir(id);
        return ResponseEntity.ok().body("Produto excluído com sucesso!");
    }
}
