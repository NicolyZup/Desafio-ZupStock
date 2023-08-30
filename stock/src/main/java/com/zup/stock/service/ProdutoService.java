package com.zup.stock.service;

import com.zup.stock.model.ProdutoModel;
import com.zup.stock.model.dto.ProdutoAllDTO;
import com.zup.stock.model.dto.ProdutoPutDTO;
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

    public List<ProdutoAllDTO> listar(){
        List<ProdutoModel> produtos = produtoRepository.findAll();
        List<ProdutoAllDTO> produtosDTOs = new ArrayList<>();

        for(ProdutoModel produto : produtos){
            ProdutoAllDTO produtoDTO = new ProdutoAllDTO();

            produtoDTO.setNome(produto.getNome());
            produtoDTO.setDescricao(produto.getDescricao());
            produtoDTO.setPreco(produto.getPreco());
            produtoDTO.setQuantidade(produto.getQuantidade());

            produtosDTOs.add(produtoDTO);
        }
        return produtosDTOs;
    }

    public Optional<ProdutoAllDTO> listarPorId(Long id){

        Optional<ProdutoModel> produtoModel = produtoRepository.findById(id);

        ProdutoAllDTO produtoDTO = new ProdutoAllDTO();
        produtoDTO.setNome(produtoModel.get().getNome());
        produtoDTO.setDescricao(produtoModel.get().getDescricao());
        produtoDTO.setPreco(produtoModel.get().getPreco());
        produtoDTO.setQuantidade(produtoModel.get().getQuantidade());
        return Optional.of(produtoDTO);
    }

    public ProdutoAllDTO criar(ProdutoAllDTO produtoDTO){
        ProdutoModel produtoModel = new ProdutoModel();

        produtoModel.setNome(produtoDTO.getNome());
        produtoModel.setDescricao(produtoDTO.getDescricao());
        produtoModel.setPreco(produtoDTO.getPreco());
        produtoModel.setQuantidade(produtoDTO.getQuantidade());

        produtoRepository.save(produtoModel);
        return produtoDTO;
    }

    public ProdutoPutDTO alterar(Long id, ProdutoPutDTO produtoDTO){
        Optional<ProdutoModel> produtoOptional = produtoRepository.findById(id);

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

        ProdutoPutDTO produtoDTOResponse = new ProdutoPutDTO();

        produtoDTOResponse.setNome(produtoOptional.get().getNome());
        produtoDTOResponse.setDescricao(produtoOptional.get().getDescricao());
        produtoDTOResponse.setPreco(produtoOptional.get().getPreco());
        produtoDTOResponse.setQuantidade(produtoOptional.get().getQuantidade());

        return produtoDTOResponse;
    }

    public void excluir(Long id){
        Optional<ProdutoModel> produtoModel = produtoRepository.findById(id);

        produtoRepository.deleteById(id);
    }
}
