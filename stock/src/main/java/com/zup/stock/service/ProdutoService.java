package com.zup.stock.service;

import com.zup.stock.exception.IdNaoLocalizadoException;
import com.zup.stock.exception.ParametroInvalidoException;
import com.zup.stock.model.ProdutoModel;
import com.zup.stock.model.dto.ProdutoDTO;
import com.zup.stock.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> listar(){
        List<ProdutoModel> produtos = produtoRepository.findAll();
        List<ProdutoDTO> produtosDTOs = new ArrayList<>();

        for(ProdutoModel produto : produtos){
            ProdutoDTO produtoDTO = new ProdutoDTO();

            produtoDTO.setNome(produto.getNome());
            produtoDTO.setDescricao(produto.getDescricao());
            produtoDTO.setPreco(produto.getPreco());
            produtoDTO.setQuantidade(produto.getQuantidade());

            produtosDTOs.add(produtoDTO);
        }
        return produtosDTOs;
    }

    public Optional<ProdutoDTO> listarPorId(Long id){
        try {
            Optional<ProdutoModel> produtoModel = produtoRepository.findById(id);

            //EXCEÇÃO DE PRODUTO NÃO ENCONTRADO
            if (produtoModel.isEmpty()) {
                throw new IdNaoLocalizadoException("Produto não localizado/existente.");
            }

            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNome(produtoModel.get().getNome());
            produtoDTO.setDescricao(produtoModel.get().getDescricao());
            produtoDTO.setPreco(produtoModel.get().getPreco());
            produtoDTO.setQuantidade(produtoModel.get().getQuantidade());

            return Optional.of(produtoDTO);
        }catch (NumberFormatException e) {
            throw new ParametroInvalidoException("Parâmetro inválido.");
        }
    }

    public ProdutoDTO criar(ProdutoDTO produtoDTO){
        ProdutoModel produtoModel = new ProdutoModel();

        produtoModel.setNome(produtoDTO.getNome());
        produtoModel.setDescricao(produtoDTO.getDescricao());
        produtoModel.setPreco(produtoDTO.getPreco());
        produtoModel.setQuantidade(produtoDTO.getQuantidade());

        produtoRepository.save(produtoModel);
        return produtoDTO;
    }

    public ProdutoDTO alterar(Long id, ProdutoDTO produtoDTO){
        Optional<ProdutoModel> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isEmpty()) {
            throw new IdNaoLocalizadoException("Produto não localizado/existente.");
        }

        if (produtoDTO.getNome() != null){
            produtoOptional.get().setNome(produtoDTO.getNome());
        }
        if (produtoDTO.getDescricao() != null){
            produtoOptional.get().setDescricao(produtoDTO.getDescricao());
        }
        if (produtoDTO.getPreco() != null){
            produtoOptional.get().setPreco(produtoDTO.getPreco());
        }
        if(produtoDTO.getQuantidade() != null){
            produtoOptional.get().setQuantidade(produtoDTO.getQuantidade());
        }

        produtoRepository.save(produtoOptional.get());

        ProdutoDTO produtoDTOResponse = new ProdutoDTO();

        produtoDTOResponse.setNome(produtoOptional.get().getNome());
        produtoDTOResponse.setDescricao(produtoOptional.get().getDescricao());
        produtoDTOResponse.setPreco(produtoOptional.get().getPreco());
        produtoDTOResponse.setQuantidade(produtoOptional.get().getQuantidade());

        return produtoDTOResponse;
    }

    public void excluir(Long id){
        Optional<ProdutoModel> produtoModel = produtoRepository.findById(id);
        if(produtoModel.isEmpty()){
            throw new IdNaoLocalizadoException("Produto não localizado/existente.");
        }
        produtoRepository.deleteById(id);
    }
}
