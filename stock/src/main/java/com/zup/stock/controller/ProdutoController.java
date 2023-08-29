package com.zup.stock.controller;

import com.zup.stock.exception.ParametroInvalidoException;
import com.zup.stock.model.ProdutoModel;
import com.zup.stock.model.dto.ProdutoDTO;
import com.zup.stock.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ZupStock/produtos")
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
    public ResponseEntity<Optional<ProdutoDTO>> listarProdutoPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(produtoService.listarPorId(id));
        }catch (NumberFormatException e){
            throw new ParametroInvalidoException("Parâmetro inválido.");
        }
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO){
        ProdutoDTO produtoNovo = produtoService.criar(produtoDTO);
        return new ResponseEntity<>(produtoNovo, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProdutoDTO> alterarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO){
        ProdutoDTO produtoNovo = produtoService.alterar(id, produtoDTO);
        return ResponseEntity.ok(produtoNovo);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id){
        produtoService.excluir(id);
        return ResponseEntity.ok().body("Produto excluído com sucesso!");
    }
}
